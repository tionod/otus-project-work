package net.otus.edu.utils;

import org.openqa.selenium.By;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    // EPAM
    public static final String BASE_URL = "https://events.epam.com/";
    public static final By EVENT_GLOBAL_LOADER_LOCATOR = By.xpath("//*[@class='evnt-global-loader']");
    // LOGGER
    public static final String GO_TO_PAGE = "Переход на страницу: {}";
    // REMOTE DRIVER
    public static final String BROWSER_NAME = "browserName";
    public static final String BROWSER_VERSION = "browserVersion";
    public static final String ENABLE_VNC = "enableVNC";
    public static final String ENABLE_VIDEO = "enableVideo";
    public static final String ENABLE_LOGS = "enableLogs";

}
