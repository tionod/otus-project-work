package net.otus.edu.pages.epam.events;

import net.otus.edu.pages.AbstractPage;
import org.openqa.selenium.WebDriver;

import static net.otus.edu.utils.EpamConstants.BASE_URL;

public class EventPage extends AbstractPage {
    private static final String URL = BASE_URL + "events";

    protected EventPage(WebDriver driver) {
        super(driver);
    }

    public EventPage open() {
        driver.get(URL);
        return this;
    }
}
