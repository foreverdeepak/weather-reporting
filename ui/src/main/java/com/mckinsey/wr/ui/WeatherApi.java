package com.mckinsey.wr.ui;

import com.mckinsey.wr.db.repository.Weather;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Collection;

/**
 * Created by deepakc on 10/04/17.
 */
@Path("/")
public interface WeatherApi {

    @GET
    @Path("/weather")
    Collection<Weather> getWeatherInfo(@QueryParam("limit") int limit, @QueryParam("offset") int offset);
}
