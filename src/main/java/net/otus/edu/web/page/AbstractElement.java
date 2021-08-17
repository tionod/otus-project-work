package net.otus.edu.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class AbstractElement extends AbstractPage {
    protected int number;

    protected AbstractElement(WebDriver driver, int number) {
        super(driver);
        this.number = number;
    }

    protected String getElementXPath(String pattern) {
        return String.format(pattern, this.number);
    }

    protected By getElementLocator(String pattern) {
        return By.xpath(getElementXPath(pattern));
    }
}
