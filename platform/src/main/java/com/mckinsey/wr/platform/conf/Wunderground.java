package com.mckinsey.wr.platform.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by deepakc on 10/04/17.
 */
public class Wunderground {

    @JsonProperty("api-url")
    private String apiUrl;

    @JsonProperty("api-key")
    private String apiKey;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return apiUrl + "/" + apiKey;
    }
}
