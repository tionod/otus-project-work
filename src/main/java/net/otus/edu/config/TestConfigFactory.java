package net.otus.edu.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

public class TestConfigFactory {
    private final Config config;
    private WebConfig webConfig;

    private TestConfigFactory() {
        config = ConfigFactory.parseResources("test.conf")
                .withFallback(ConfigFactory.systemEnvironment())
                .withFallback(ConfigFactory.systemProperties());
    }

    public synchronized WebConfig getWebConfig() {
        if (webConfig == null) {
            webConfig = ConfigBeanFactory.create(config.getConfig("web"), WebConfig.class);
        }
        return webConfig;
    }

    public static synchronized TestConfigFactory getInstance(){
        return new TestConfigFactory();
    }
}
