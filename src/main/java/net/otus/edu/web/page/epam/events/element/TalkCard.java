package net.otus.edu.web.page.epam.events.element;

import net.otus.edu.web.page.AbstractElement;
import net.otus.edu.web.page.epam.events.TalkPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TalkCard extends AbstractElement {
    private static final String TALK_CARD_PATTERN = "(//div[contains(@class, 'evnt-talk-card')])[%s]";
    private static final String LANGUAGE_XPATH = "//p[@class='language']";
    private static final String NAME_XPATH = "//div[@class='evnt-talk-name']//span";

    public TalkCard(WebDriver driver, int number) {
        super(driver, number);
    }

    private final String talkCardLocator = getElementXPath(TALK_CARD_PATTERN);

    public TalkPage open() {
        getWebElement(getElementLocator(TALK_CARD_PATTERN)).click();
        return new TalkPage(driver);
    }

    public String getName() {
        By nameLocator = By.xpath(talkCardLocator + NAME_XPATH);
        return getWebElement(nameLocator).getText();
    }

    public String getLanguage() {
        By languageLocator = By.xpath(talkCardLocator + LANGUAGE_XPATH);
        return getWebElement(languageLocator).getText();
    }
}
