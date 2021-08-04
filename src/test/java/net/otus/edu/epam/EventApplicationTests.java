package net.otus.edu.epam;

import net.otus.edu.pages.epam.events.EventPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.otus.edu.webdriver.WebDriverService.*;

class EventApplicationTests {
    private static final Logger LOGGER = LogManager.getLogger(EventApplicationTests.class);

    @BeforeEach
    public void setUp() {
        setDriver();
    }

    @AfterEach
    public void tearDown() {
        closeDriver();
    }

    @Test
    void viewUpcomingEvents() {
        EventPage eventPage = new EventPage(getDriver()).open();
        LOGGER.info("Переход на страницу: {}", eventPage.getTitle());
        String tabName = "Upcoming events";
        Integer currentCounterValue = eventPage.clickTabByName(tabName).getTabCounter();
        LOGGER.info("Текущий показатель счетчика вкладки {}: {}", tabName, currentCounterValue);
        Integer eventCardsCount = eventPage.getEventCards().size();
        LOGGER.info("Текущие кол-во карточек с событиями: {}", eventCardsCount);
        Assertions.assertEquals(eventCardsCount, currentCounterValue,
                "текущее значение счетчика не соответствует реальному кол-ву событий");
    }

    @Test
    void viewUpcomingEvents2() {
        EventPage eventPage = new EventPage(getDriver()).open();
        LOGGER.info("Переход на страницу: {}", eventPage.getTitle());
        String tabName = "Upcoming events";
        Integer currentCounterValue = eventPage.clickTabByName(tabName).getTabCounter();
        LOGGER.info("Текущий показатель счетчика вкладки {}: {}", tabName, currentCounterValue);
        Integer eventCardsCount = eventPage.getEventCards().size();
        LOGGER.info("Текущие кол-во карточек с событиями: {}", eventCardsCount);
        Assertions.assertEquals(eventCardsCount, currentCounterValue,
                "текущее значение счетчика не соответствует реальному кол-ву событий");
    }
}
