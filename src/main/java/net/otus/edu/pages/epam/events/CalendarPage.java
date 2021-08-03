package net.otus.edu.pages.epam.events;

import net.otus.edu.pages.AbstractPage;
import org.openqa.selenium.WebDriver;

import static net.otus.edu.utils.EpamConstants.BASE_URL;

public class CalendarPage extends AbstractPage {
    private static final String URL = BASE_URL + "calendar";

    protected CalendarPage(WebDriver driver) {
        super(driver);
    }

    public CalendarPage open() {
        driver.get(URL);
        return this;
    }
}
