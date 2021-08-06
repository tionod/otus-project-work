package net.otus.edu.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.otus.edu.config.TestConfigFactory;
import net.otus.edu.enums.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class WebDriverFactory {
    private static final TestConfigFactory CONFIG_FACTORY = TestConfigFactory.getInstance();

    private WebDriverFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static WebDriver create() {
        return create(Browser.getByValue(CONFIG_FACTORY.getWebConfig().getBrowserName()));
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
}
