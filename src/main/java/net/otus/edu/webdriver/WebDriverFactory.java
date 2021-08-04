package net.otus.edu.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.otus.edu.config.TestConfigFactory;
import net.otus.edu.enums.Browser;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class WebDriverFactory {
    private static final TestConfigFactory CONFIG_FACTORY = TestConfigFactory.getInstance();

    private WebDriverFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static WebDriver create() {
        return create(CONFIG_FACTORY.getWebConfig().getBrowser());
    }

    public static WebDriver create(Browser browser) {
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case OPERA:
                WebDriverManager.operadriver().setup();
                return new OperaDriver();
            default:
                return null;
        }
    }

    public static WebDriver create(Browser type, MutableCapabilities options) {
        switch (type) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver((ChromeOptions) options);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver((FirefoxOptions) options);
            case OPERA:
                WebDriverManager.operadriver().setup();
                return new OperaDriver((OperaOptions) options);
            default:
                return null;
        }
    }
}
