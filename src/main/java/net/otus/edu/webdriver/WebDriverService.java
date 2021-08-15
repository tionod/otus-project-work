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

    private static final TestConfigFactory CONFIG_FACTORY = TestConfigFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(WebDriverService.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void setDriver() {
        LOGGER.info("Инициализация драйвера и создание сессии");
        String runType = System.getProperty("run");
        if (runType == null) {
            DRIVER.set(initLocalDriver());
        } else {
            if ("remote".equals(runType)) {
                DRIVER.set(initRemoteDriver());
            } else if ("local".equals(runType)) {
                DRIVER.set(initLocalDriver());
            }
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
        String browserName = System.getProperty("browser");
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
        WebConfig config = CONFIG_FACTORY.getWebConfig();
        Properties prop = System.getProperties();
        DesiredCapabilities capabilities;

        if (prop.getProperty("run").equals("remote") && prop.getProperty("browser") != null) {
            capabilities = setCapabilities(
                    prop.getProperty(BROWSER_NAME),
                    prop.getProperty(BROWSER_VERSION),
                    Boolean.valueOf(prop.getProperty(ENABLE_VNC)),
                    Boolean.valueOf(prop.getProperty(ENABLE_VIDEO)),
                    Boolean.valueOf(prop.getProperty(ENABLE_LOGS)));
        } else {
            capabilities = setCapabilities(config);
        }

        try {
            driver = new RemoteWebDriver(URI.create(config.getSelenoidUrl()).toURL(), capabilities);
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }

    private static DesiredCapabilities setCapabilities(WebConfig config) {
        return setCapabilities(config.getBrowserName(), config.getBrowserVersion(),
                config.getEnableVNC(), config.getEnableVideo(), config.getEnableLogs());
    }

    private static DesiredCapabilities setCapabilities(String browserName, String browserVersion, Boolean enableVNC, Boolean enableVideo, Boolean enableLogs) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(BROWSER_NAME, browserName);
        capabilities.setCapability(BROWSER_VERSION, browserVersion);

        Map<String, Object> options = new HashMap<>();
        options.put(ENABLE_VNC, enableVNC);
        options.put(ENABLE_VIDEO, enableVideo);
        options.put(ENABLE_LOGS, enableLogs);

        capabilities.setCapability("selenoid:options", options);
        return capabilities;
    }
}
