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

}
