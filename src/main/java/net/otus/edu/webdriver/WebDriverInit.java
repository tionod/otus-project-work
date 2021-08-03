package net.otus.edu.webdriver;

import com.epam.healenium.SelfHealingDriver;
import net.otus.edu.enums.Browsers;
import org.openqa.selenium.WebDriver;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class WebDriverInit {
    private WebDriverInit() {throw new IllegalStateException("Utility class");}

    public static WebDriver initDriver() {
        String browserName = System.getProperty("browser");
        WebDriver driver;
        if (browserName != null && Browsers.containValue(browserName)) {
            driver = WebDriverFactory.create(Browsers.valueOf(browserName.toUpperCase(Locale.ROOT)));
        } else {
            driver = WebDriverFactory.create(Browsers.CHROME);
        }
        if (driver != null) {
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static SelfHealingDriver initSelfHealingDriver() {
        return SelfHealingDriver.create(initDriver());
    }
}
