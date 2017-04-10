package com.mckinsey.wunderground.client;

import com.mckinsey.wr.platform.PlatformConfig;
import com.mckinsey.wr.platform.conf.Config;
import com.mckinsey.wr.platform.conf.Wunderground;
import com.mckinsey.wr.platform.rest.HTTPClientProxy;
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
