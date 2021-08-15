package net.otus.edu.epam;

import net.otus.edu.utils.EventDateParser;
import net.otus.edu.web.page.epam.events.EventCardElement;
import net.otus.edu.web.page.epam.events.EventPage;
import net.otus.edu.web.page.epam.events.FilterPanel;
import net.otus.edu.web.page.epam.events.VideoPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static net.otus.edu.utils.Constants.GO_TO_PAGE;
import static net.otus.edu.web.page.epam.events.FilterPanel.Action.SHOW;
import static net.otus.edu.webdriver.WebDriverService.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тестирование платформы 'events.epam.com'")
class EventApplicationTests {
    private static final Logger LOGGER = LogManager.getLogger(EventApplicationTests.class);
    private static final Random RND = new Random();

    @BeforeEach
    public void setUp() {
        setDriver(Run.REMOTE);
    }

    @AfterEach
    public void tearDown() {
        closeDriver();
    }

    @Test
    @DisplayName("Просмотр предстоящих мероприятий")
    void viewUpcomingEvents() {
        // Step 1 - Пользователь переходит на вкладку events
        EventPage eventPage = openEventPage();
        // Step 2 - На странице отображаются карточки предстоящих мероприятий. Количество карточек равно счетчику на кнопке Upcoming Events.
        Integer eventCardsCount = eventPage.getEventCardsCount();
        LOGGER.info("Текущие кол-во карточек с событиями: {}", eventCardsCount);
        Integer currentCounterValue = eventPage.getTabCounter();
        LOGGER.info("Текущий показатель счетчика вкладки 'Upcoming events': {}", currentCounterValue);
        assertEquals(eventCardsCount, currentCounterValue,
                "Текущее значение счетчика не соответствует реальному кол-ву событий");
    }

    @Test
    @DisplayName("Просмотр карточек мероприятий")
    void viewEventCards() {
        // Step 1 - Пользователь переходит на вкладку events
        // Step 2 - Пользователь нажимает на Upcoming Events
        // Step 3 - На странице отображаются карточки предстоящих мероприятий.
        EventPage eventPage = openEventTab("Upcoming events");
        // Step 4 - В карточке указана информация о мероприятии: • язык • название мероприятия • дата мероприятия • информация о регистрации • список спикеров
        EventCardElement rndEventCardElement = eventPage.getEventCardElement(RND.nextInt(eventPage.getEventCardsCount()) + 1);
        assertAll(
                () -> assertTrue(rndEventCardElement.isVisibleLanguage(), "не отображен язык события"),
                () -> assertTrue(rndEventCardElement.isVisibleEventName(), "не отображено имя события"),
                () -> assertTrue(rndEventCardElement.isVisibleDate(), "не отображена дата проведения события"),
                // Note: На момент написания теста, поле информации о регистрации отсутствовало в карточках события
                () -> assertTrue(rndEventCardElement.isVisibleSpeaker(), "не отображена информация о спикере")
        );
    }

    @Test
    @DisplayName("Валидация дат предстоящих мероприятий")
    void checkUpcomingEventDate() {
        // Step 1 - Пользователь переходит на вкладку events
        // Step 2 - Пользователь нажимает на Upcoming Events
        // Step 3 - На странице отображаются карточки предстоящих мероприятий.
        EventPage eventPage = openEventTab("Upcoming events");
        // Step 3 - Даты проведения мероприятий больше или равны текущей дате (или текущая дата находится в диапазоне дат проведения)
        EventCardElement eventCardElement = eventPage.getEventCardElement(1);
        String eventDate = eventCardElement.getDate();
        LocalDate start = EventDateParser.getFirstDateAtString(eventDate);
        LocalDate end = EventDateParser.getLastDateAtString(eventDate);
        LocalDate now = LocalDate.now();
        assertTrue(
                (start.isAfter(now) || start.isEqual(now)) && (end.isAfter(now) || start.isEqual(now)),
                "диапазон дат проведения мероприятий находиться в прошлом");
    }

    @Test
    @DisplayName("Просмотр прошедших мероприятий в Канаде")
    void checkPastEventAfterLocationFilter() {
        // Step 1 - Пользователь переходит на вкладку events
        // Step 2 - Пользователь нажимает на Past Events
        EventPage eventPage = openEventTab("Past Events");
        // Step 3 - Пользователь нажимает на Location в блоке фильтров и выбирает Canada в выпадающем списке
        eventPage.goToFilter().locationFilter(SHOW).selectLocation("Canada");
        eventPage.waitCardLoader();
        // Step 4 - На странице отображаются карточки прошедших мероприятий. Количество карточек равно счетчику на кнопке Past Events. Даты проведенных мероприятий меньше текущей даты.
        assertAll(
                () -> assertTrue(eventPage.existEvents(), "на странице отсутствуют события"),
                () -> assertEquals(eventPage.getEventCardsCount(), eventPage.getTabCounter(),
                        "количество карточек не равно счетчику вкладки"),
                () -> assertTrue(
                        EventDateParser.getLastDateAtString(eventPage.getEventCardElement(eventPage.getTabCounter()).getDate())
                                .isBefore(LocalDate.now()),
                        "финальная дата события больше текущей даты")
        );
    }

    @Test
    @DisplayName("Фильтрация докладов по категориям")
    void filterReportsByCategory() {
        // Step 1 - Пользователь переходит на вкладку (Video) Talks Library
        VideoPage videoPage = openVideoPage();
        // Step 2 - Пользователь нажимает на More Filters
        FilterPanel filterPanel = videoPage.goToFilter().moreFilters(SHOW);
        // Step 3 - Пользователь выбирает: Category – Testing, Location – Belarus, Language – English, На вкладке фильтров
        filterPanel.categoryFilter(SHOW).selectCategory("Testing");
        filterPanel.locationFilter(SHOW).selectLocation("Belarus");
        filterPanel.languageFilter(SHOW).selectLanguage("English");
        // Step 4 - На странице отображаются карточки соответствующие правилам выбранных фильтров
        assertTrue(videoPage.existTalkCards());
    }

    @Test
    @DisplayName("Поиск докладов по ключевому слову 'QA'")
    void searchTalkByWord() {
        // Step 1 - Пользователь переходит на вкладку (Video) Talks Library
        VideoPage videoPage = openVideoPage();
        // Step 2 - Пользователь вводит ключевое слово QA в поле поиска
        videoPage.goToFilter().search("QA");
        videoPage.waitGlobalLoader();
        // Step 3 - На странице отображаются доклады, содержащие в названии ключевое слово поиска
        assertTrue(videoPage.existTalkCards());

    }

    private EventPage openEventPage() {
        EventPage eventPage = new EventPage(getDriver()).open();
        LOGGER.info(GO_TO_PAGE, eventPage.getTitle());
        assertNotNull(eventPage);
        return eventPage;
    }

    private VideoPage openVideoPage() {
        VideoPage videoPage = new VideoPage(getDriver()).open();
        LOGGER.info(GO_TO_PAGE, videoPage.getTitle());
        assertNotNull(videoPage);
        return videoPage;
    }

    private EventPage openEventTab(String tabName) {
        EventPage eventPage = openEventPage();
        LOGGER.info("Переход на вкладку: {}", tabName);
        eventPage.clickTabByName(tabName);
        assertTrue(eventPage.existEvents());
        return eventPage;
    }
}
