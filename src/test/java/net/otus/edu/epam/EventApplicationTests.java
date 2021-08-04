package net.otus.edu.epam;

import net.otus.edu.web.page.epam.events.EventPage;
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
        // Step 1 - Пользователь переходит на вкладку events
        EventPage eventPage = openEventPage();
        // Step 2 - Пользователь нажимает на Upcoming Events
        String tabName = "Upcoming events";
//        String tabName = "Past Events";
        eventPage.clickTabByName(tabName);
        // Step 3 - На странице отображаются карточки предстоящих мероприятий. Количество карточек равно счетчику на кнопке Upcoming Events.
        Integer eventCardsCount = eventPage.getEventCards().size();
        LOGGER.info("Текущие кол-во карточек с событиями: {}", eventCardsCount);
        Integer currentCounterValue = eventPage.getTabCounter();
        LOGGER.info("Текущий показатель счетчика вкладки {}: {}", tabName, currentCounterValue);
        Assertions.assertEquals(eventCardsCount, currentCounterValue,
                "Текущее значение счетчика не соответствует реальному кол-ву событий");
    }

    @Test
    void viewEventCards() {
        // Step 1 - Пользователь переходит на вкладку events
        EventPage eventPage = openEventPage();
        // Step 2 - Пользователь нажимает на Upcoming Events
        String tabName = "Upcoming events";
        eventPage.clickTabByName(tabName);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(eventPage.getEventCards()),
                () -> Assertions.assertTrue(true)
        );
    }

    private EventPage openEventPage() {
        EventPage eventPage = new EventPage(getDriver()).open();
        LOGGER.info("Переход на страницу: {}", eventPage.getTitle());
        return eventPage;
    }
}
