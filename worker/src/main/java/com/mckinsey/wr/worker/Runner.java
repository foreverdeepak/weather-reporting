package com.mckinsey.wr.worker;

import com.mckinsey.wr.db.repository.DbConfig;
import com.mckinsey.wr.db.repository.WeatherRepository;
import com.mckinsey.wr.platform.Bootstrap;
import com.mckinsey.wr.platform.KryoSerializer;
import com.mckinsey.wr.platform.PlatformConfig;
import com.mckinsey.wr.platform.conf.Config;
import com.mckinsey.wr.platform.conf.WeatherConfig;
import com.mckinsey.wr.platform.kafka.PubsubProvider;
import com.mckinsey.wunderground.client.model.WeatherResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by deepakc on 10/04/17.
 */
@Configuration
@Import({PlatformConfig.class, DbConfig.class})
public class Runner {

    static String country;

    @Bean
    public CountryWeatherWorker countryWeatherWorker(Config config, WeatherRepository repository, PubsubProvider pubsubProvider) throws Exception{
        WeatherConfig.Country cc = config.getWeatherConfig().getCountryByName(country);
        if (cc == null || cc.getTopic() == null) {
            throw new Exception("No such country exist..");
        }
        CountryWeatherWorker worker =  new CountryWeatherWorker(repository);
        pubsubProvider.registerConsumer("weather-queue", cc.getTopic(), worker, new KryoSerializer<>(WeatherResponse.class));
        return worker;
    }

    public static void main(String[] args) throws Exception {
        country = System.getProperty("weather.country");
        if (country == null) {
            throw new Exception("No country specified..");
        }
        Bootstrap bootstrap = new Bootstrap(Runner.class, true);
        bootstrap.load();
    }
}
