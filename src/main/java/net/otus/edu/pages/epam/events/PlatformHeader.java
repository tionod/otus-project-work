package net.otus.edu.pages.epam.events;

import net.otus.edu.pages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PlatformHeader extends AbstractPage {
    private static final String NAV_LINK = "//a[@class='nav-link']";
    private final By eventLink = By.xpath(NAV_LINK + "[.='Events']");
    private final By calendarLink = By.xpath(NAV_LINK + "[.='Calendar']");
    private final By videoLink = By.xpath(NAV_LINK + "[.='Video']");


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

    public VideoPage openVideoPage() {
        getWebElement(videoLink).click();
        return new VideoPage(driver);
    }
}
