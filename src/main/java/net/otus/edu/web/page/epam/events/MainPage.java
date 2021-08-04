package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.openqa.selenium.WebDriver;

import static net.otus.edu.utils.EpamConstants.BASE_URL;

public class MainPage extends AbstractPage {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get(BASE_URL);
        return this;
    }

    public PlatformHeader goToPlatformHeader() {
        return new PlatformHeader(driver);
    }
}
