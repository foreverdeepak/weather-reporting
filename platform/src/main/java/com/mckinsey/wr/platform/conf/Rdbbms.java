package com.mckinsey.wr.platform.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by deepakc on 10/04/17.
 */
public class Rdbbms {

    private String id;
    private String host;
    private int port;
    private String username;
    private String password;

    @JsonProperty("db-name")
    private String dbName;

    @JsonProperty("min-pool-size")
    private int minPoolSize;

    @JsonProperty("max-pool-size")
    private int maxPoolSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
