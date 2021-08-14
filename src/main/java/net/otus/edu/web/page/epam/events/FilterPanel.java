package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FilterPanel extends AbstractPage {

    public FilterPanel(WebDriver driver) {
        super(driver);
    }

    private static final Logger LOGGER = LogManager.getLogger(FilterPanel.class);

    private static final By SHOW_HIDE_FILTERS = By.xpath("//div[contains(@class, 'evnt-filters-heading-cell')][@data-toggle='collapse']");
    private static final String LOCATION_FILTER_XPATH = "//div[@id='filter_location']";
    private static final String INPUT_TEXT_FIELD = "/following-sibling::div//input[@type='text']";
    private static final String LOCATION_CHECKBOX_PATTERN = "//label[contains(@class,'form-check-label')][contains(@data-value, '%s')]";

    public FilterPanel moreFilters(Action action) {
        expandElementArea(SHOW_HIDE_FILTERS, action);
        return this;
    }

    public FilterPanel locationFilter(Action action) {
        expandElementArea(By.xpath(LOCATION_FILTER_XPATH), action);
        return this;
    }

    public void pickLocation(String location) {
        getWebElement(By.xpath(LOCATION_FILTER_XPATH + INPUT_TEXT_FIELD)).sendKeys(location);
        getWebElement(By.xpath(String.format(LOCATION_CHECKBOX_PATTERN, location))).click();
    }

    private void expandElementArea(By locator, Action action) {
        WebElement filterButton = getWebElement(locator);
        String areaExpanded = filterButton.getAttribute("aria-expanded");
        if (action == Action.SHOW) {
            if (areaExpanded == null || areaExpanded.equals("false")) {
                filterButton.click();
            } else {
                LOGGER.info("спойлер уже раскрыт");
            }
        } else if (action == Action.HIDE) {
            if (areaExpanded.equals("true")) {
                filterButton.click();
            } else {
                LOGGER.info("спойлер уже скрыт");
            }
        }
    }

    public enum Action {
        SHOW, HIDE
    }
}
