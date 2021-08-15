package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.openqa.selenium.WebDriver;

import static net.otus.edu.utils.Constants.BASE_URL;

public class CalendarPage extends AbstractPage {
    private static final String URL = BASE_URL + "calendar";

    public CalendarPage(WebDriver driver) {
        super(driver);
    }

    public CalendarPage open() {
        driver.get(URL);
        return this;
    }
}
