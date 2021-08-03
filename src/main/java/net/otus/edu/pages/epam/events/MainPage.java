package net.otus.edu.pages.epam.events;

import net.otus.edu.pages.AbstractPage;
import org.openqa.selenium.WebDriver;

public class MainPage extends AbstractPage {
    private static final String URL = "https://events.epam.com/";
    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get(URL);
        return this;
    }
}
