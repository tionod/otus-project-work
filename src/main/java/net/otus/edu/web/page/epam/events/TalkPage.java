package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class TalkPage extends AbstractPage {
    private static final By CATEGORIES = By.xpath("//div[@class='evnt-talk-details topics']/p[.='Categories']/..//label");
    private static final By LOCATION = By.xpath("//div[contains(@class, 'evnt-talk-details location')]/span");
    private static final By LANGUAGE = By.xpath("//div[contains(@class, 'evnt-talk-details language')]/span");

    public TalkPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        getWebElements(CATEGORIES).forEach(category -> categories.add(category.getText()));
        return categories;
    }

    public String getLocation() {
        return getWebElement(LOCATION).getText();
    }

    public String getLanguage() {
        return getWebElement(LANGUAGE).getText();
    }
}
