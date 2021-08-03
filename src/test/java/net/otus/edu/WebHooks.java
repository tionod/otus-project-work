package net.otus.edu;

import net.otus.edu.webdriver.WebDriverInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

public class WebHooks {
    private static final Logger LOGGER = LogManager.getLogger(WebHooks.class);
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    @BeforeAll
    public static void setUp() {
        LOGGER.info("Инициализация драйвера и создание сессии");
        driver = WebDriverInit.initDriver();
    }

    @AfterEach
    public void tearDown() {
        LOGGER.info("Завершение сессии драйвера");
        if (driver != null) {
            driver.quit();
        }
    }
}
