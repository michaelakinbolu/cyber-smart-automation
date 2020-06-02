package cyber.smart.automation.perf.stepLib;

import org.apache.commons.io.FileUtils;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JmeterExecute {

    private final static Logger logger = LoggerFactory.getLogger(JmeterExecute.class);

    public static void jmeterExecution(String scriptLocation, String reportLocation, String reportPrefix) {

        try {
            //JMeter Engine
            StandardJMeterEngine jmeter = new StandardJMeterEngine();
            File f = new File(scriptLocation);

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
}
