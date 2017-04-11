package com.deepak.wr.supervisor;

/**
 * Created by deepakc on 10/04/17.
 */
public interface WeatherSupervisor {

    void execute() throws Exception;

    void destroy() throws Exception;

}
