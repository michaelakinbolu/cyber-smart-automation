package cyber.smart.automation.utils;

import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class HeadlessChrome implements DriverSource {

    @Override
    public  WebDriver newDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users/shafarmichaelakinbolu/Desktop/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-first-run");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-gpu");

        return new ChromeDriver(chromeOptions);
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }
}
