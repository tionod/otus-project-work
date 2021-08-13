package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static net.otus.edu.utils.EpamConstants.BASE_URL;

public class EventPage extends AbstractPage {
    private static final String URL = BASE_URL + "events";
    private static final String EVENTS_TAB_PATTERN = "//a[@class='evnt-tab-link nav-link %s']/span[@class='evnt-tab-text %s']";
    private static final String TAB_BY_NAME_PATTERN = "//a[contains(@class, 'evnt-tab-link nav-link')]/span[.='%s']";
    private static final By ACTIVE_DESKTOP_EVENTS_TAB_COUNTER = By.xpath(String.format(EVENTS_TAB_PATTERN, "active", "desktop") + "/following-sibling::span[contains(@class, 'evnt-tab-counter evnt-label')]");
    private static final By EVENT_CARD = By.xpath("//div[contains(@class, 'evnt-event-card')]");
    private static final By EVENT_GLOBAL_LOADER = By.xpath("//*[@class='evnt-global-loader']");
    private static final By EVENT_CARD_LOADER = By.xpath("//*[@class='evnt-cards-loading']");
    private static final By FOOTER_NAVIGATOR_WRAPPER = By.xpath("//div[@class='evnt-footer-navigation-wrapper']");

    public EventPage(WebDriver driver) {
        super(driver);
    }

    public EventPage open() {
        driver.get(URL);
        return this;
    }

    public void clickTabByName(String tabName) {
        getWebElement(By.xpath(String.format(TAB_BY_NAME_PATTERN, tabName))).click();
        waitEventLoader();
    }

    private void waitEventLoader() {
        waitDisappearance(EVENT_GLOBAL_LOADER);
    }

    public void waitCardLoader() {
        waitDisappearance(EVENT_CARD_LOADER);
    }

    public void scrollToFooter() {
        js.scrollTo(getWebElement(FOOTER_NAVIGATOR_WRAPPER));
    }

    public Integer getTabCounter() {
        return Integer.parseInt(getWebElement(ACTIVE_DESKTOP_EVENTS_TAB_COUNTER).getText());
    }

    public Integer getEventCardsCount() {
        waitCardLoader();
        return getWebElements(EVENT_CARD).size();
    }

    public boolean isExistEvent() {
        waitEventLoader();
        return driver.findElement(EVENT_CARD).isDisplayed();
    }

    public EventCardElement getEventCardElement(int number) {
        return new EventCardElement(driver, number);
    }
}
