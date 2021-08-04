package net.otus.edu.web.page.epam.events;

import net.otus.edu.web.page.AbstractPage;
import org.openqa.selenium.WebDriver;

import static net.otus.edu.utils.EpamConstants.BASE_URL;

public class VideoPage extends AbstractPage {
    private static final String URL = BASE_URL + "video?f%5B0%5D%5Bmedia%5D%5B%5D=Video";

    public VideoPage(WebDriver driver) {
        super(driver);
    }

    public VideoPage open() {
        driver.get(URL);
        return this;
    }
}
