package cyber.smart.automation.utils;

import java.io.*;
import java.util.Properties;

public class PropertyReader {

	private static Properties prop = null;
	private static Properties testData = null;

	private static synchronized void loadProp() throws IOException {
		prop = new Properties();
		File propFile = new File("src/test/resources/properties/cyber-smart-automation.properties");
		InputStream inputStream = new FileInputStream(propFile);
		prop.load(inputStream);
		inputStream.close();
	}

	private static synchronized void loadTestData() throws IOException {
		testData = new Properties();
		File dataFile = new File("src/test/resources/testdata/test-data.properties");
		InputStream testInputStream = new FileInputStream(dataFile);
		testData.load(testInputStream);
		testInputStream.close();
	}


	public static String getPropValues(String key) throws IOException {
		if (prop == null) {
			loadProp();
		}
		return prop.getProperty(key);
	}

	public static String getTestData(String key) throws IOException {
		if (testData == null) {
			loadTestData();
		}
		return testData.getProperty(key);
	}

}

