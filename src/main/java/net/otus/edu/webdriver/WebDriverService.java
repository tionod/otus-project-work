package net.otus.edu.webdriver;

import com.epam.healenium.SelfHealingDriver;
import net.otus.edu.enums.Browser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class WebDriverService {
    private WebDriverService() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger LOGGER = LogManager.getLogger(WebDriverService.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void setDriver() {
        LOGGER.info("Инициализация драйвера и создание сессии");
        DRIVER.set(initSelfHealingDriver());
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void closeDriver() {
        LOGGER.info("Завершение сессии драйвера");
        DRIVER.get().close();
        DRIVER.remove();
    }

    protected static WebDriver initDriver() {
        String browserName = System.getProperty("browser");
        WebDriver driver;
        if (browserName != null && Browser.containValue(browserName)) {
            driver = WebDriverFactory.create(Browser.valueOf(browserName.toUpperCase(Locale.ROOT)));
        } else {
            driver = WebDriverFactory.create();
        }
        if (driver != null) {
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    protected static SelfHealingDriver initSelfHealingDriver() {
        return SelfHealingDriver.create(initDriver());
    }
}
