package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import net.otus.edu.web.page.epam.events.element.TalkCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static net.otus.edu.utils.Constants.BASE_URL;
import static net.otus.edu.utils.Constants.EVENT_GLOBAL_LOADER_LOCATOR;

public class TalksLibraryPage extends AbstractPage {
    private static final String URL = BASE_URL + "video?f%5B0%5D%5Bmedia%5D%5B%5D=Video";
    private static final By EVENT_TALK_CARD = By.xpath("//div[@class='evnt-talk-card']");
    private static final By EVENT_TALK_SEARCH_RESULT = By.xpath("//div[contains(@class, 'evnt-results-cell')]//span");

    public TalksLibraryPage(WebDriver driver) {
        super(driver);
    }

    public TalksLibraryPage open() {
        driver.get(URL);
        return this;
    }

    public boolean existTalkCards() {
        waitGlobalLoader();
        return driver.findElement(EVENT_TALK_CARD).isDisplayed();
    }

    public Integer getTalkCardsCount() {
        waitGlobalLoader();
        return getWebElements(EVENT_TALK_CARD).size();
    }

    public Integer getTalkCardsCounter() {
        waitGlobalLoader();
        return Integer.parseInt(getWebElement(EVENT_TALK_SEARCH_RESULT).getText());
    }

    public TalkCard getTalkCard(int number) {
        return new TalkCard(driver, number);
    }

    public void waitGlobalLoader() {
        waitDisappearance(EVENT_GLOBAL_LOADER_LOCATOR);
    }

    public FilterPanel goToFilter() {
        return new FilterPanel(driver);
    }
}
