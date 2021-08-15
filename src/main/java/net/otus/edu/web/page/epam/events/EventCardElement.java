package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class EventCardElement extends AbstractPage {
    private static final String EVENT_CARD_PATTERN = "(//div[contains(@class, 'evnt-event-card')])[%s]";
    private static final String LANGUAGE_XPATH = "//p[@class='language']/span";
    private static final String EVENT_NAME_XPATH = "//div[@class='evnt-event-name']//span";
    private static final String DATE_XPATH = "//span[@class='date']";
    private static final String SPEAKER_XPATH = "//div[@class='evnt-speaker']";

    protected int number;

    public EventCardElement(WebDriver driver, int number) {
        super(driver);
        this.number = number;
    }

    private String getEventCardXPath() {
        return String.format(EVENT_CARD_PATTERN, number);
    }

    public String getLanguage() {
        By languageLocator = By.xpath(getEventCardXPath() + LANGUAGE_XPATH);
        return getWebElement(languageLocator).getText();
    }

    public boolean isVisibleLanguage() {
        return !getLanguage().isEmpty();
    }

    public String getEventName() {
        By eventNameLocator = By.xpath(getEventCardXPath() + EVENT_NAME_XPATH);
        return getWebElement(eventNameLocator).getText();
    }

    public boolean isVisibleEventName() {
        return !getEventName().isEmpty();
    }

    public String getDate() {
        By dateLocator = By.xpath(getEventCardXPath() + DATE_XPATH);
        return getWebElement(dateLocator).getText();
    }

    public boolean isVisibleDate() {
        return !getDate().isEmpty();
    }

    public String getSpeakers() {
        By speakerLocator = By.xpath(getEventCardXPath() + SPEAKER_XPATH);
        List<WebElement> speakers = getWebElements(speakerLocator);
        List<String> speakerList = new ArrayList<>();
        for (WebElement speaker : speakers) {
            if (!speaker.getAttribute("data-name").equals("Speaker")) {
                speakerList.add(speaker.getAttribute("data-name"));
            }
        }

        if (speakerList.isEmpty()) {
            return speakers.get(0).getAttribute("data-job-title");
        } else {
            return String.join(", ", speakerList);
        }
    }

    public boolean isVisibleSpeaker() {
        return !getSpeakers().isEmpty() || getSpeakers().equals("Information about the speaker will be available soon");
    }
}
