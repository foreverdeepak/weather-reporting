package com.deepak.wr.ui;

import com.deepak.wr.db.repository.DbConfig;
import com.deepak.wr.db.repository.WeatherRepository;
import com.deepak.wr.platform.PlatformConfig;
import com.deepak.wr.ui.impl.DefaultWeatherApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by deepakc on 10/04/17.
 */
@Configuration
@Import({PlatformConfig.class, DbConfig.class})
@ImportResource("classpath:META-INF/spring/ui-bootstrap.xml")
public class UiConfig {

    @Bean (name = "weatherApi")
    public WeatherApi weatherApi(WeatherRepository repository) {
        return new DefaultWeatherApi(repository);
    }
}
