package com.deepak.wr.platform.rest;

/**
 * Created by deepakc on 10/04/17.
 */
public interface HTTPClientProxy {

    <T> T createProxy(String url, Class<T> type) throws Exception;

    <T> ClientProxyBuilder<T> proxyBuilder();

    interface ClientProxyBuilder<T> {
        ClientProxyBuilder<T> url(String url);

        ClientProxyBuilder<T> type(Class<T> type);

        T build() throws Exception;

    }
}
