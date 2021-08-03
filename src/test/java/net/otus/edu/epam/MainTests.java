package net.otus.edu.epam;

import net.otus.edu.WebHooks;
import net.otus.edu.pages.epam.events.EventPage;
import net.otus.edu.pages.epam.events.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

class MainTests extends WebHooks {

    @Test
    void firstTest() {
        WebDriver driver = getDriver();
        EventPage eventPage = new MainPage(driver).open().goToPlatformHeader().openEventPage();

        Assertions.assertEquals(driver.getTitle(), eventPage.getTitle());

    }
}
