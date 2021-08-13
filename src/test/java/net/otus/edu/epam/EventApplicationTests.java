package net.otus.edu.epam;

import net.otus.edu.web.page.epam.events.EventCardElement;
import net.otus.edu.web.page.epam.events.EventPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static net.otus.edu.webdriver.WebDriverService.*;

class EventApplicationTests {
    private static final Logger LOGGER = LogManager.getLogger(EventApplicationTests.class);
    private static final Random RND = new Random();

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
        // Step 2 - На странице отображаются карточки предстоящих мероприятий. Количество карточек равно счетчику на кнопке Upcoming Events.
        Integer eventCardsCount = eventPage.getEventCardsCount();
        LOGGER.info("Текущие кол-во карточек с событиями: {}", eventCardsCount);
        Integer currentCounterValue = eventPage.getTabCounter();
        LOGGER.info("Текущий показатель счетчика вкладки 'Upcoming events': {}", currentCounterValue);
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
        // Step 3 - На странице отображаются карточки предстоящих мероприятий.
        Assertions.assertTrue(eventPage.isExistEvent());
        // Step 4 - В карточке указана информация о мероприятии: • язык • название мероприятия • дата мероприятия • информация о регистрации • список спикеров
        EventCardElement rndEventCardElement = eventPage.getEventCardElement(RND.nextInt(eventPage.getEventCardsCount()) + 1);
        Assertions.assertAll(
                () -> Assertions.assertTrue(rndEventCardElement.isVisibleLanguage(), "не отображен язык события"),
                () -> Assertions.assertTrue(rndEventCardElement.isVisibleEventName(), "не отображено имя события"),
                () -> Assertions.assertTrue(rndEventCardElement.isVisibaleDate(), "не отображена дата проведения события"),
                // Note: На момент написания теста, поле информации о регистрации отсутствовало в карточках события
                () -> Assertions.assertTrue(rndEventCardElement.isVisibleSpeaker(), "не отображена информация о спикере")
        );
    }

    private EventPage openEventPage() {
        EventPage eventPage = new EventPage(getDriver()).open();
        LOGGER.info("Переход на страницу: {}", eventPage.getTitle());
        return eventPage;
    }
}
