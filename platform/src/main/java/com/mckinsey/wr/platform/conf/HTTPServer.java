package com.mckinsey.wr.platform.conf;

/**
 * Created by deepakc on 10/04/17.
 */
public class HTTPServer {

    private String host;
    private String context;
    private int port;
    private String scheme;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getContextPath() {
        if (context == null) {
            context = "/";
        }
        return ("/" + context).replace("//", "/");
    }

    public String getContextUrl() {
        StringBuilder urlBuilder = new StringBuilder(scheme + "://" + this.getHost());
        if (port != 80 || port != 883) {
            urlBuilder.append(":" + port);
        }
        if (context != null && !context.equals("/")) {
            urlBuilder.append("/" + context);
        }
        String url = urlBuilder.toString();
        return url.replace("//", "/").replace(":/", "://");
    }
}
