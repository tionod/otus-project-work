package net.otus.edu.config;

import lombok.Data;

@Data
public class WebConfig {

    private String browserName;
    private String browserVersion;
    private Boolean enableVNC;
    private Boolean enableVideo;
    private Boolean enableLogs;
    private String selenoidUrl;

}
