package com.deepak.wr.platform;

import com.deepak.wr.platform.conf.Config;
import com.deepak.wr.platform.conf.ConfigReader;
import com.deepak.wr.platform.kafka.PubsubProvider;
import com.deepak.wr.platform.kafka.impl.DefaultPubsubProvider;
import com.deepak.wr.platform.rest.impl.DefaultHTTPClientProxy;
import com.deepak.wr.platform.rest.HTTPClientProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;

/**
 * Created by deepakc on 10/04/17.
 */
@Configuration
@EnableAspectJAutoProxy
public class PlatformConfig {

    @Bean(name = "config")
    public Config config() throws IOException {
        return ConfigReader.readConfig();
    }

    @Bean
    public HTTPClientProxy httpClientProxy() {
        return new DefaultHTTPClientProxy();
    }

    @Bean
    public PubsubProvider pubsubProvider(Config config) {
        return new DefaultPubsubProvider(config);
    }

}
