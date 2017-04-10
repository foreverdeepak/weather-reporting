package com.mckinsey.wr.ui;

import com.mckinsey.wr.db.repository.DbConfig;
import com.mckinsey.wr.db.repository.WeatherRepository;
import com.mckinsey.wr.platform.PlatformConfig;
import com.mckinsey.wr.ui.impl.DefaultWeatherApi;
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
