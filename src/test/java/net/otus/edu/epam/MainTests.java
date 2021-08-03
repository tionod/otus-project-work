package net.otus.edu.epam;

import net.otus.edu.pages.epam.events.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTests extends WebHooks{

    @Test
    void firstTest() {
        Assertions.assertNotNull(new MainPage(getDriver()).open());
    }
}
