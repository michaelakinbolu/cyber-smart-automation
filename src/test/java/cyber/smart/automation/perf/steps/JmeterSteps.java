package cyber.smart.automation.perf.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import kg.apc.cmdtools.PluginsCMD;
import kg.apc.cmdtools.ReporterTool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import cyber.smart.automation.utils.PropertyReader;

public class JmeterSteps extends ReporterTool {

    private static final int NINETY_FIFTH_INDEX = 5;
    private static final int NINETIETH_INDEX = 4;
    private static final int AVERAGE_INDEX = 2;
    private static final int PERCENT_INDEX = 9;
    private static final int THROUGHPUT_INDEX = 10;
    private static String jmxFileLocation;
    private String reportPrefix;
    private final Logger logger = LoggerFactory.getLogger(JmeterSteps.class);
    private static String reportLocation;
    private static String jmxDomain;

    static {
        try {
            jmxFileLocation = System.getProperty("user.dir") + File.separator + PropertyReader.getPropValues("jmeter.jmx.location");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            reportLocation = System.getProperty("user.dir") + File.separator + PropertyReader.getPropValues("jmeter.report.location");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            jmxDomain = PropertyReader.getPropValues("jmeter.jmx.endpoint");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String jmeterHome;
    {
        try {
            jmeterHome = System.getProperty("user.dir") + File.separator + PropertyReader.getPropValues("jmeter.home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Given("^I have the \"([^\"]*)\" test in my script directory$")
    public void checkScriptExistInDirectory(String scriptName) {
        // Check if script file exists
        String jmxFile = jmxFileLocation + scriptName;
        File f = new File(jmxFile);
        assertThat(f).exists().isFile();
    }

    @When("^the test script \"([^\"]*)\" is executed$")
    public void theTestIsExecuted(String scriptName) {
        reportPrefix = StringUtils.substringBefore(scriptName, ".");
        try {

            //Initialize JMeter
            JMeterUtils.setJMeterHome(jmeterHome);
            JMeterUtils.loadJMeterProperties(jmeterHome + File.separator + "bin" + File.separator + "jmeter.properties");
            JMeterUtils.loadProperties(jmeterHome + File.separator + "bin" + File.separator + "user.properties");
            JMeterUtils.setProperty("jmeter.domain", jmxDomain);


            JMeterUtils.initLocale();

            //JMeter Engine
            StandardJMeterEngine jmeter = new StandardJMeterEngine();

            //Initialize JMeter SaveService
            SaveService.loadProperties();

            //Load existing .jmx Test Plan
            String scriptLocation = jmxFileLocation + scriptName;
            File f = new File(scriptLocation);
            assertThat(f).exists().isFile();

            HashTree testPlanTree = SaveService.loadTree(f);
            Summariser summer = null;
            String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary"); //Defaults to Summary Report.
            if (summariserName.length() > 0) {
                summer = new Summariser(summariserName);
            }

            //Store execution results into a .jtl file, we can save file as csv also
            String reportFile = reportLocation + reportPrefix + "JTLreport.jtl";
            File reportOutputJTLFile = new File(reportFile);

            if (reportOutputJTLFile.exists()) {
                FileUtils.forceDelete(reportOutputJTLFile);
            }

            ResultCollector jtllogger = new ResultCollector(summer);
            jtllogger.setFilename(reportFile);
            testPlanTree.add(testPlanTree.getArray()[0], jtllogger);

            // Run JMeter Test
            jmeter.configure(testPlanTree);
            jmeter.run();

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }


    @Then("^the report is generated correctly with following average,error rate,90th percentile and 95th percentile$")
    public void theReportIsGeneratedCorrectly(List<List<String>> resultValues) {

        List<List<String>> data = resultValues;

        String jtlReportPath = reportLocation + reportPrefix + "JTLreport.jtl";
        File jtlFile = new File(jtlReportPath);
        assertThat(jtlFile).exists().isFile();

        generateHtmlReport(jtlReportPath);

        try {
            File reportOutputCSVFile = new File(reportLocation + reportPrefix + "AggReport.csv");

            if (reportOutputCSVFile.exists()) {
                FileUtils.forceDelete(reportOutputCSVFile);
            }
            String str = "--generate-csv " + reportLocation + reportPrefix + "AggReport.csv"
                    + " --input-jtl " + reportLocation + reportPrefix + "JTLreport.jtl"
                    + " --aggregate-rows yes --plugin-type AggregateReport";
            String[] args = str.split(" +");

            int expResult = 0;
            int result = this.processParams(PluginsCMD.argsArrayToListIterator(args));
            assertThat(result).isEqualTo(expResult);

            String aggregateReportPath = reportLocation + reportPrefix + "AggReport.csv";
            File file = new File(aggregateReportPath);
            assertThat(file).exists().isFile();

            data.stream().skip(1).forEach(list -> {

                String transName = list.get(0);
                String[] testNumber = transName.split("_");
                String avg = list.get(1);
                double error = Double.valueOf(list.get(2));
                String agg90th = list.get(3);
                String agg95th = list.get(4);
                double throughput = Double.valueOf(list.get(5));

                try {
                    String currentLine = Files.readAllLines(Paths.get(aggregateReportPath)).get(Integer.valueOf(testNumber[0]));
                    String[] aggRep = currentLine.split(",");
                    String errPercent = StringUtils.substringBefore(aggRep[PERCENT_INDEX], ".");
                    String average = aggRep[AVERAGE_INDEX];
                    String aggNinetieth = aggRep[NINETIETH_INDEX];
                    String aggNinetyFifth = aggRep[NINETY_FIFTH_INDEX];
                    String aggThroughput = aggRep[THROUGHPUT_INDEX];

                    assertThat(Double.parseDouble(errPercent)).isLessThanOrEqualTo(error);
                    assertThat(Integer.parseInt(average)).isLessThanOrEqualTo(Integer.parseInt(avg));
                    assertThat(Integer.parseInt(aggNinetieth)).isLessThanOrEqualTo(Integer.parseInt(agg90th));
                    assertThat(Integer.parseInt(aggNinetyFifth)).isLessThanOrEqualTo(Integer.parseInt(agg95th));
                    assertThat(Double.parseDouble(aggThroughput)).isGreaterThanOrEqualTo(throughput);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }



    private void generateHtmlReport(String reportFile) {

        try {
            File reportOutputFolderAsFile = new File(reportLocation + "html");

            if (reportOutputFolderAsFile.exists()) {
                FileUtils.forceDelete(reportOutputFolderAsFile);
            }

            final String reportOutputFolderAbsPath = reportOutputFolderAsFile.getAbsolutePath();
            JMeterUtils.setProperty("jmeter.reportgenerator.exporter.html.property.output_dir", reportOutputFolderAbsPath);
            ReportGenerator generator = new ReportGenerator(reportFile, null);
            generator.generate();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}