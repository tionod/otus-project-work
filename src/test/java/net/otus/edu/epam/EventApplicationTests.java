package net.otus.edu.epam;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import net.otus.edu.utils.EventDateParser;
import net.otus.edu.web.page.epam.events.EventPage;
import net.otus.edu.web.page.epam.events.FilterPanel;
import net.otus.edu.web.page.epam.events.TalkPage;
import net.otus.edu.web.page.epam.events.TalksLibraryPage;
import net.otus.edu.web.page.epam.events.element.EventCard;
import net.otus.edu.web.page.epam.events.element.TalkCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static net.otus.edu.utils.Constants.GO_TO_PAGE;
import static net.otus.edu.web.page.epam.events.FilterPanel.Action.HIDE;
import static net.otus.edu.web.page.epam.events.FilterPanel.Action.SHOW;
import static net.otus.edu.webdriver.WebDriverService.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Тестирование платформы 'events.epam.com'")
class EventApplicationTests {
    private static final Logger LOGGER = LogManager.getLogger(EventApplicationTests.class);
    private static final Random RND = new Random();

    @BeforeEach
    public void setUp() {
        setDriver(Run.LOCAL);
    }

    @AfterEach
    public void tearDown() {
        closeDriver();
    }

    @Test
    @DisplayName("Просмотр предстоящих мероприятий")
    void viewUpcomingEvents() {
        EventPage eventPage = openEventPage();
        Integer eventCardsCount = eventPage.getEventCardsCount();
        LOGGER.info("Текущие кол-во карточек с событиями: {}", eventCardsCount);
        Integer currentCounterValue = eventPage.getTabCounter();
        LOGGER.info("Текущий показатель счетчика вкладки 'Upcoming events': {}", currentCounterValue);
        Allure.step("На странице отображаются карточки предстоящих мероприятий. Количество карточек равно счетчику на кнопке Upcoming Events");
        assertEquals(eventCardsCount, currentCounterValue,
                "Текущее значение счетчика не соответствует реальному кол-ву событий");
    }

    @Test
    @DisplayName("Просмотр карточек мероприятий")
    void viewEventCards() {
        EventPage eventPage = openEventTab("Upcoming events");
        EventCard rndEventCard = eventPage.getEventCard(RND.nextInt(eventPage.getEventCardsCount()) + 1);
        Allure.step("В карточке указана информация о мероприятии: • язык • название мероприятия • дата мероприятия • список спикеров");
        assertAll(
                () -> assertTrue(rndEventCard.isVisibleLanguage(), "не отображен язык события"),
                () -> assertTrue(rndEventCard.isVisibleEventName(), "не отображено имя события"),
                () -> assertTrue(rndEventCard.isVisibleDate(), "не отображена дата проведения события"),
                () -> assertTrue(rndEventCard.isVisibleSpeaker(), "не отображена информация о спикере")
        );
        Allure.description("Note: На момент написания теста, поле информации о регистрации отсутствовало в карточках события");
    }

    @Test
    @DisplayName("Валидация дат предстоящих мероприятий")
    void checkUpcomingEventDate() {
        EventPage eventPage = openEventTab("Upcoming events");
        EventCard eventCard = eventPage.getEventCard(1);
        String eventDate = eventCard.getDate();
        LocalDate start = EventDateParser.getFirstDateAtString(eventDate);
        LocalDate end = EventDateParser.getLastDateAtString(eventDate);
        LocalDate now = LocalDate.now();
        Allure.step("Даты проведения мероприятий больше или равны текущей дате (или текущая дата находится в диапазоне дат проведения)");
        assertTrue(
                (start.isAfter(now) || start.isEqual(now)) && (end.isAfter(now) || start.isEqual(now)),
                "диапазон дат проведения мероприятий находиться в прошлом");
    }

    @Test
    @DisplayName("Просмотр прошедших мероприятий в Канаде")
    void checkPastEventAfterLocationFilter() {
        EventPage eventPage = openEventTab("Past Events");
        Allure.step("Пользователь нажимает на Location в блоке фильтров и выбирает Canada в выпадающем списке");
        eventPage.goToFilter().locationFilter(SHOW).selectLocation("Canada");
        eventPage.waitCardLoader();
        Allure.step("На странице отображаются карточки прошедших мероприятий. Количество карточек равно счетчику на кнопке Past Events. Даты проведенных мероприятий меньше текущей даты.");
        assertAll(
                () -> assertTrue(eventPage.existEvents(), "на странице отсутствуют события"),
                () -> assertEquals(eventPage.getEventCardsCount(), eventPage.getTabCounter(),
                        "количество карточек не равно счетчику вкладки"),
                () -> assertTrue(
                        EventDateParser.getLastDateAtString(eventPage.getEventCard(eventPage.getTabCounter()).getDate())
                                .isBefore(LocalDate.now()),
                        "финальная дата события больше текущей даты")
        );
    }

    @Test
    @DisplayName("Фильтрация докладов по категориям")
    void filterReportsByCategory() {
        TalksLibraryPage page = openTalksLibraryPage();
        String category = "Testing";
        String location = "Belarus";
        String language = "English";
        TalksLibraryPage results = searchTalksByParams(page, category, location, language);
        TalkPage rndTalkPage = results.getTalkCard(RND.nextInt(results.getTalkCardsCount() + 1)).open();
        Allure.step("На странице отображаются карточки соответствующие правилам выбранных фильтров");
        assertAll(
                () -> assertTrue(rndTalkPage.getCategories().contains(category), String.format("в списке категорий отсутствует '%s'", category)),
                () -> assertTrue(rndTalkPage.getLocation().contains(location), "место проведения события не соответствует выбранному фильтру"),
                () -> assertTrue(rndTalkPage.getLanguage().equalsIgnoreCase(language), "язык события не соответствует выбранному фильтру")
        );
    }

    @Test
    @DisplayName("Поиск докладов по ключевому слову 'QA'")
    void searchTalkByWord() {
        TalksLibraryPage page = openTalksLibraryPage();
        String word = "QA";
        TalksLibraryPage results = searchTalksByName(page, word);
        TalkCard rndTalkCard = results.getTalkCard(RND.nextInt(results.getTalkCardsCount() + 1));
        Allure.step(String.format("На странице отображаются доклады, содержащие в названии ключевое слово поиска '%s'", word));
        assertTrue(rndTalkCard.getName().contains(word));

    }

    @Step("Пользователь открывает страницу 'Events'")
    private EventPage openEventPage() {
        EventPage eventPage = new EventPage(getDriver()).open();
        LOGGER.info(GO_TO_PAGE, eventPage.getTitle());
        assertNotNull(eventPage);
        return eventPage;
    }

    @Step("Пользователь открывает страницу 'Video / Talks Library'")
    private TalksLibraryPage openTalksLibraryPage() {
        TalksLibraryPage talksLibraryPage = new TalksLibraryPage(getDriver()).open();
        LOGGER.info(GO_TO_PAGE, talksLibraryPage.getTitle());
        assertNotNull(talksLibraryPage);
        return talksLibraryPage;
    }

    @Step("Пользователь переходит на вкладку '{tabName}'")
    private EventPage openEventTab(String tabName) {
        EventPage eventPage = openEventPage();
        LOGGER.info("Переход на вкладку: {}", tabName);
        Allure.step(String.format("Пользователь нажимает на вкладку '%s'", tabName));
        eventPage.clickTabByName(tabName);
        Allure.step("На странице отображаются карточки предстоящих мероприятий.");
        assertTrue(eventPage.existEvents());
        return eventPage;
    }

    @Step("Пользователь вводит в поиск строку: '{string}'")
    private TalksLibraryPage searchTalksByName(TalksLibraryPage page, String string) {
        Integer count = page.getTalkCardsCounter();
        page.goToFilter().search(string);
        waitResultsUpdate(page, count);
        return page;
    }

    @Step("Пользователь выполняет поиск по категории '{category}', локации '{location}' и языку '{language}'")
    private TalksLibraryPage searchTalksByParams(TalksLibraryPage page, String category, String location, String language) {
        Integer count = page.getTalkCardsCounter();
        Allure.step("Пользователь нажимает на More Filters");
        FilterPanel filterPanel = page.goToFilter().moreFilters(SHOW);
        Allure.step(String.format("Пользователь выбирает: Category – %s", category));
        filterPanel.categoryFilter(SHOW).selectCategory(category);
        Allure.step(String.format("Пользователь выбирает: Location – %s", location));
        filterPanel.locationFilter(SHOW).selectLocation(location);
        Allure.step(String.format("Пользователь выбирает: Language – %s", language));
        filterPanel.languageFilter(SHOW).selectLanguage(language).languageFilter(HIDE);
        waitResultsUpdate(page, count);
        assertTrue(page.existTalkCards());
        return page;
    }

    @Step("Ожидается обновление результатов")
    private void waitResultsUpdate(TalksLibraryPage page, Integer count) {
        while (count.equals(page.getTalkCardsCounter())) {
            page.waitGlobalLoader();
        }
    }
}
