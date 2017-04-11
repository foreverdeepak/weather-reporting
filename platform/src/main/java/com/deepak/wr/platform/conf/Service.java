package com.deepak.wr.platform.conf;

/**
 * Created by deepakc on 10/04/17.
 */
public class Service {

    private String name;
    private HTTPServer[] container;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HTTPServer[] getContainer() {
        return container;
    }

    public void setContainer(HTTPServer[] container) {
        this.container = container;
    }
}
