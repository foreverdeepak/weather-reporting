package com.deepak.wunderground.client;

import com.deepak.wr.platform.PlatformConfig;
import com.deepak.wr.platform.conf.Config;
import com.deepak.wr.platform.conf.Wunderground;
import com.deepak.wr.platform.rest.HTTPClientProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by deepakc on 10/04/17.
 */
@Configuration
@Import({PlatformConfig.class})
public class WundergroundClientConfig {

    @Bean
    public WundergroundApi wundergroundApi(Config config, HTTPClientProxy proxy) throws Exception {
        Wunderground wunderground = config.getWunderground();
        return proxy.createProxy(wunderground.getUrl(), WundergroundApi.class);
    }
}
