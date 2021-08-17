package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PlatformHeader extends AbstractPage {
    private static final String NAV_LINK_PATTERN = "//li[contains(@class, 'nav-item %s')]/a[@class='nav-link']";
    private final By eventLink = By.xpath(String.format(NAV_LINK_PATTERN, "events"));
    private final By calendarLink = By.xpath(String.format(NAV_LINK_PATTERN, "calendar"));
    private final By videoLink = By.xpath(String.format(NAV_LINK_PATTERN, "video"));

    protected PlatformHeader(WebDriver driver) {
        super(driver);
    }

    public EventPage openEventPage() {
        getWebElement(eventLink).click();
        return new EventPage(driver);
    }

    public CalendarPage openCalendarPage() {
        getWebElement(calendarLink).click();
        return new CalendarPage(driver);
    }

    public TalksLibraryPage openVideoPage() {
        getWebElement(videoLink).click();
        return new TalksLibraryPage(driver);
    }
}
