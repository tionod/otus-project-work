package net.otus.edu.epam;

import net.otus.edu.utils.EventDateParser;
import net.otus.edu.web.page.epam.events.EventCardElement;
import net.otus.edu.web.page.epam.events.EventPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static net.otus.edu.web.page.epam.events.FilterPanel.Action.SHOW;
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
        // Step 2 - Пользователь нажимает на Upcoming Events
        // Step 3 - На странице отображаются карточки предстоящих мероприятий.
        EventPage eventPage = openEventTab("Upcoming events");
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

    @Test
    void checkUpcomingEventDate() {
        // Step 1 - Пользователь переходит на вкладку events
        // Step 2 - Пользователь нажимает на Upcoming Events
        EventPage eventPage = openEventTab("Upcoming events");
        // Step 3 - Даты проведения мероприятий больше или равны текущей дате (или текущая дата находится в диапазоне дат проведения)
        EventCardElement eventCardElement = eventPage.getEventCardElement(1);
        String eventDate = eventCardElement.getDate();
        LocalDate start = EventDateParser.getFirstDateAtString(eventDate);
        LocalDate end = EventDateParser.getLastDateAtString(eventDate);
        LocalDate now = LocalDate.now();
        Assertions.assertTrue(
                (start.isAfter(now) || start.isEqual(now)) && (end.isAfter(now) || start.isEqual(now)),
                "диапазон дат проведения мероприятий находиться в прошлом");
    }

    @Test
    void checkPastEventAfterLocationFilter() {
        // Step 1 - Пользователь переходит на вкладку events
        // Step 2 - Пользователь нажимает на Past Events
        EventPage eventPage = openEventTab("Past Events");
        // Step 3 - Пользователь нажимает на Location в блоке фильтров и выбирает Canada в выпадающем списке
        eventPage.goToFilter().locationFilter(SHOW).pickLocation("Canada");
        eventPage.waitCardLoader();
        // Step 4 - На странице отображаются карточки прошедших мероприятий. Количество карточек равно счетчику на кнопке Past Events. Даты проведенных мероприятий меньше текущей даты.
        Assertions.assertAll(
                () -> Assertions.assertTrue(eventPage.isExistEvent(), "на странице отсутствуют события"),
                () -> Assertions.assertEquals(eventPage.getEventCardsCount(), eventPage.getTabCounter(),
                        "количество карточек не равно счетчику вкладки"),
                () -> Assertions.assertTrue(
                        EventDateParser.getLastDateAtString(eventPage.getEventCardElement(eventPage.getTabCounter()).getDate())
                                .isBefore(LocalDate.now()),
                        "финальная дата события больше текущей даты")
        );
    }

    private EventPage openEventPage() {
        EventPage eventPage = new EventPage(getDriver()).open();
        LOGGER.info("Переход на страницу: {}", eventPage.getTitle());
        Assertions.assertNotNull(eventPage);
        return eventPage;
    }

    private EventPage openEventTab(String tabName) {
        EventPage eventPage = openEventPage();
        LOGGER.info("Переход на вкладку: {}", tabName);
        eventPage.clickTabByName(tabName);
        Assertions.assertTrue(eventPage.isExistEvent());
        return eventPage;
    }
}
