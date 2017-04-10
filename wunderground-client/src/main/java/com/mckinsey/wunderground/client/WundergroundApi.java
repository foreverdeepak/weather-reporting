package com.mckinsey.wunderground.client;

import com.mckinsey.wunderground.client.model.WeatherResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by deepakc on 10/04/17.
 */
@Path("/")
public interface WundergroundApi {

    @GET
    @Path("conditions/q/{country}/{city}.json")
    WeatherResponse getWeatherResponse(@PathParam("country") String country, @PathParam("city") String city);

}
