package com.mckinsey.wr.worker;

import com.mckinsey.wr.db.repository.DbConfig;
import com.mckinsey.wr.db.repository.WeatherRepository;
import com.mckinsey.wr.platform.Bootstrap;
import com.mckinsey.wr.platform.PlatformConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by deepakc on 10/04/17.
 */
@Configuration
@Import({PlatformConfig.class, DbConfig.class})
public class Runner {

    @Bean
    public CountryWeatherWorker countryWeatherWorker(WeatherRepository repository) {
        return new CountryWeatherWorker(repository);
    }


    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(Runner.class, true);
        bootstrap.load();
    }
}
