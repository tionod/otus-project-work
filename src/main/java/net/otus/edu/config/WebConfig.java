package net.otus.edu.config;

import lombok.Data;
import net.otus.edu.enums.Browser;

@Data
public class WebConfig {

    private Browser browser;
    private String browserVersion;

}
