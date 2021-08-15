package net.otus.edu.webdriver;

import net.otus.edu.config.TestConfigFactory;
import net.otus.edu.config.WebConfig;
import net.otus.edu.enums.Browser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static net.otus.edu.utils.Constants.*;

public class WebDriverService {
    private WebDriverService() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger LOGGER = LogManager.getLogger(WebDriverService.class);
    private static final Properties PROPS = System.getProperties();
    private static final WebConfig WEB_CONFIG = TestConfigFactory.getInstance().getWebConfig();
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void setDriver(Run place) {
        LOGGER.info("Инициализация драйвера и создание сессии");
        switch (place) {
            case REMOTE:
                DRIVER.set(initRemoteDriver());
                break;
            case LOCAL:
            default:
                DRIVER.set(initLocalDriver());
        }
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void closeDriver() {
        LOGGER.info("Завершение сессии драйвера");
        DRIVER.get().quit();
        DRIVER.remove();
    }

    protected static WebDriver initLocalDriver() {
        String browserName = System.getProperty(BROWSER);
        WebDriver driver;
        if (browserName != null) {
            driver = WebDriverFactory.create(Browser.getByValue(browserName));
        } else {
            driver = WebDriverFactory.create();
        }
        if (driver != null) {
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    protected static WebDriver initRemoteDriver() {
        WebDriver driver = null;
        DesiredCapabilities capabilities = setCapabilities();
        try {
            driver = new RemoteWebDriver(URI.create(WEB_CONFIG.getSelenoidUrl()).toURL(), capabilities);
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    private static DesiredCapabilities setCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        Map<String, Object> selenoidOptions = new HashMap<>();

        String browser = (PROPS.getProperty(BROWSER) != null ? PROPS.getProperty(BROWSER) : WEB_CONFIG.getBrowserName());
        String version = (PROPS.getProperty(BROWSER_VERSION) != null ? PROPS.getProperty(BROWSER_VERSION) : WEB_CONFIG.getBrowserVersion());
        Boolean enableVNC = (PROPS.getProperty(ENABLE_VNC) != null ? Boolean.valueOf(PROPS.getProperty(ENABLE_VNC)) : WEB_CONFIG.getEnableVNC());
        Boolean enableVideoRecord = (PROPS.getProperty(ENABLE_VIDEO) != null ? Boolean.valueOf(PROPS.getProperty(ENABLE_VIDEO)) : WEB_CONFIG.getEnableVideo());
        Boolean enableLogs = (PROPS.getProperty(ENABLE_LOGS) != null ? Boolean.valueOf(PROPS.getProperty(ENABLE_LOGS)) : WEB_CONFIG.getEnableLogs());

        caps.setCapability(BROWSER_NAME, browser);
        LOGGER.info(CAPABILITY, BROWSER_NAME, browser);

        caps.setCapability(BROWSER_VERSION, version);
        LOGGER.info(CAPABILITY, BROWSER_VERSION, version);

        selenoidOptions.put(ENABLE_VNC, enableVNC);
        selenoidOptions.put(ENABLE_VIDEO, enableVideoRecord);
        selenoidOptions.put(ENABLE_LOGS, enableLogs);
        caps.setCapability(SELENOID_OPTIONS, selenoidOptions);
        LOGGER.info(CAPABILITY, SELENOID_OPTIONS, selenoidOptions);

        return caps;
    }

    public enum Run {
        LOCAL, REMOTE
    }
}
