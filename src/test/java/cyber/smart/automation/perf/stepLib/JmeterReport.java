package cyber.smart.automation.perf.stepLib;

import kg.apc.cmdtools.PluginsCMD;
import kg.apc.cmdtools.ReporterTool;
import org.apache.commons.io.FileUtils;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.log4j.Logger;

import java.io.File;

import static org.junit.Assert.*;


import static org.assertj.core.api.Assertions.assertThat;

public class JmeterReport extends ReporterTool {

    private final static Logger logger = Logger.getLogger(JmeterReport.class);

    public void generateJmeterReports(String reportLocation, String reportPrefix) {

        generateAggregateReport(reportLocation, reportPrefix);

        try {
            String jtlReportPath = reportLocation + reportPrefix + "JTLreport.jtl";
            File jtlFile = new File(jtlReportPath);
            assertThat(jtlFile).exists().isFile();

            File reportOutputFolderAsFile = new File(reportLocation + "html");

            if (reportOutputFolderAsFile.exists()) {
                FileUtils.forceDelete(reportOutputFolderAsFile);
            }

            final String reportOutputFolderAbsPath = reportOutputFolderAsFile.getAbsolutePath();
            JMeterUtils.setProperty("jmeter.reportgenerator.exporter.html.property.output_dir", reportOutputFolderAbsPath);
            ReportGenerator generator = new ReportGenerator(jtlReportPath, null);
            generator.generate();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void generateAggregateReport(String reportLocation, String reportPrefix) {

        try {
            File reportOutputCSVFile = new File(reportLocation + reportPrefix + "AggReport.csv");

            if (reportOutputCSVFile.exists()) {
                FileUtils.forceDelete(reportOutputCSVFile);
            }
            String str = "--generate-csv " + reportOutputCSVFile.getAbsolutePath() + " "
                    + "--input-jtl " + reportLocation + reportPrefix + "JTLreport.jtl "
                    + "--aggregate-rows yes --plugin-type AggregateReport";

            String[] args = str.split(" +");
            int expResult = 0;
            int result = this.processParams(PluginsCMD.argsArrayToListIterator(args));
            logger.info("Report Generator Status : " + Integer.toString(result));
            assertThat(result).isEqualTo(expResult);
            //assertEquals(expResult, result);
            assertTrue(66 < reportOutputCSVFile.length());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
