package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Locale;

public class FilterPanel extends AbstractPage {

    public FilterPanel(WebDriver driver) {
        super(driver);
    }

    private static final Logger LOGGER = LogManager.getLogger(FilterPanel.class);

    private static final By SHOW_HIDE_FILTERS = By.xpath("//div[contains(@class, 'evnt-filters-heading-cell')][@data-toggle='collapse']");
    private static final By SEARCH_FIELD = By.xpath("//div[@class='evnt-search-filter']/input");
    private static final String LOCATION_FILTER_XPATH = "//div[@id='filter_location']";
    private static final String CATEGORY_FILTER_XPATH = "//div[@id='filter_category']";
    private static final String LANGUAGE_FILTER_XPATH = "//div[@id='filter_language']";
    private static final String INPUT_TEXT_FIELD = "/following-sibling::div//input[@type='text']";
    private static final String LOCATION_CHECKBOX_PATTERN = "//label[contains(@class,'form-check-label')][.='%s']";

    public void search(String value) {
        getWebElement(SEARCH_FIELD).sendKeys(value);
    }

    public FilterPanel moreFilters(Action action) {
        expandElementArea(SHOW_HIDE_FILTERS, action);
        return this;
    }

    public FilterPanel locationFilter(Action action) {
        expandElementArea(By.xpath(LOCATION_FILTER_XPATH), action);
        return this;
    }

    public void selectLocation(String location) {
        inputValueAndSelect(LOCATION_FILTER_XPATH, location);
    }

    public FilterPanel categoryFilter(Action action) {
        expandElementArea(By.xpath(CATEGORY_FILTER_XPATH), action);
        return this;
    }

    public void selectCategory(String category) {
        inputValueAndSelect(CATEGORY_FILTER_XPATH, category);
    }

    public FilterPanel languageFilter(Action action) {
        expandElementArea(By.xpath(LANGUAGE_FILTER_XPATH), action);
        return this;
    }

    public void selectLanguage(String language) {
        selectCheckbox(language.toUpperCase(Locale.ROOT));
    }

    private void selectCheckbox(String name) {
        getWebElement(By.xpath(String.format(LOCATION_CHECKBOX_PATTERN, name))).click();
    }

    private void inputValueAndSelect(String filterXPath, String value) {
        getWebElement(By.xpath(filterXPath + INPUT_TEXT_FIELD)).sendKeys(value);
        selectCheckbox(value);
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
