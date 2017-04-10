package com.mckinsey.wr.db.repository.impl;

import com.mckinsey.wr.db.repository.Weather;
import com.mckinsey.wr.db.repository.WeatherRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by deepakc on 10/04/17.
 */
public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String WEATHER_COLUMNS = "COUNTRY, CITY, P_TEMP_F, P_TEMP_C, L_TEMP_F, L_TEMP_F, P_OBS_TIME, L_OBS_TIME";

    private static final String INSERT_STMT = "INSERT INTO WEATHER (" + WEATHER_COLUMNS + ") " +
            "VALUES (" + "?,?,?,?,?,?,?,?" + ")" ;

    private static final String UPDATE_STMT = "UPDATE WEATHER SET P_TEMP_F = ?, P_TEMP_C = ?, L_TEMP_F = ?, " +
            "L_TEMP_F = ?, P_OBS_TIME = ?, L_OBS_TIME = ? " +
            "WHERE COUNTRY = ? AND CITY = ?" ;

    private static final String SELECT_ALL_STMT = "SELECT " +  WEATHER_COLUMNS + " FROM WEATHER LIMIT ? OFFSET ?";

    private static final String SELECT_WEATHER_STMT = "SELECT " +  WEATHER_COLUMNS + " FROM WEATHER " +
            "WHERE COUNTRY = ? AND CITY = ?";

    private JdbcTemplate jdbcTemplate;

    public WeatherRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void saveWeatherInfo(WeatherDTO weatherDTO) {
        Weather weather = jdbcTemplate.query(SELECT_WEATHER_STMT, p -> {
            p.setString(1, weatherDTO.getCountry());
            p.setString(2, weatherDTO.getCity());
        }, new WeatherMapper()).get(0);

        if (weather != null) {
            jdbcTemplate.update(UPDATE_STMT, p -> {
                p.setDouble(1, weather.getlTempF());
                p.setDouble(2, weather.getlTempC());
                p.setDouble(3, weatherDTO.getTempF());
                p.setDouble(4, weatherDTO.getTempC());
                p.setDate(5, new Date(weather.getLatestObservationTime().getTime()));
                p.setDate(6, new Date(weatherDTO.getRecordTime().getTime()));
            });
        } else {
            jdbcTemplate.update(INSERT_STMT, p -> {
                p.setString(1, weatherDTO.getCountry());
                p.setString(2, weatherDTO.getCity());
                p.setDouble(3, weather.getlTempF());
                p.setDouble(4, weather.getlTempC());
                p.setDouble(5, weatherDTO.getTempF());
                p.setDouble(6, weatherDTO.getTempC());
                p.setDate(7, new Date(weather.getLatestObservationTime().getTime()));
                p.setDate(8, new Date(weatherDTO.getRecordTime().getTime()));
            });
        }

    }

    @Override
    public Collection<Weather> getWeatherInfo(int offset, int limit) {
        return jdbcTemplate.query(SELECT_ALL_STMT, p -> {
            p.setInt(1, limit);
            p.setInt(2, offset);
        }, new WeatherMapper());
    }

    private class WeatherMapper implements RowMapper<Weather> {

        @Override
        public Weather mapRow(ResultSet r, int i) throws SQLException {
            Weather weather = new Weather();
            weather.setCountry(r.getString(1));
            weather.setCity(r.getString(2));
            weather.setpTempF(r.getDouble(3));
            weather.setpTempC(r.getDouble(4));
            weather.setlTempF(r.getDouble(5));
            weather.setlTempC(r.getDouble(6));
            weather.setPreviousObservationTime(r.getDate(7));
            weather.setLatestObservationTime(r.getDate(8));
            return weather;
        }
    }

}
