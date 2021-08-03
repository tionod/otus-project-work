package net.otus.edu.pages.epam.events;

import net.otus.edu.pages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static net.otus.edu.utils.EpamConstants.BASE_URL;

public class EventPage extends AbstractPage {
    private static final String URL = BASE_URL + "events";
    private static final String EVENTS_TAB_PATTERN = "//a[@class='evnt-tab-link nav-link %s']/span[@class='evnt-tab-text %s']";
    private static final String TAB_BY_NAME_PATTERN = "//a[contains(@class, 'evnt-tab-link nav-link')]/span[.='%s']";
    private static final By ACTIVE_DESKTOP_EVENTS_TAB_COUNTER = By.xpath(String.format(EVENTS_TAB_PATTERN, "active", "desktop") + "/following-sibling::span[contains(@class, 'evnt-tab-counter evnt-label')]");
    private static final By EVENT_CARD = By.xpath("//div[@class='evnt-cards-container']//div[@class='evnt-card-wrapper']");

    public EventPage(WebDriver driver) {
        super(driver);
    }

    public EventPage open() {
        driver.get(URL);
        return this;
    }

    public EventPage clickTabByName(String tabName) {
        getWebElement(By.xpath(String.format(TAB_BY_NAME_PATTERN, tabName))).click();
        return this;
    }

    public Integer getTabCounter() {
        return Integer.parseInt(getWebElement(ACTIVE_DESKTOP_EVENTS_TAB_COUNTER).getText());
    }

    public List<WebElement> getEventCards() {
        return getWebElements(EVENT_CARD);
    }
}
