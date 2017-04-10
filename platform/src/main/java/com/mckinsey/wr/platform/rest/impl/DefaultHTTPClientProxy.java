package com.mckinsey.wr.platform.rest.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.mckinsey.wr.platform.rest.HTTPClientProxy;
import org.apache.cxf.Bus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepakc on 10/04/17.
 */
public class DefaultHTTPClientProxy implements HTTPClientProxy, ApplicationContextAware {

    private ApplicationContext context;

    public DefaultHTTPClientProxy() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public <T> T createProxy(String serviceName, Class<T> type) throws Exception {
        ClientProxyBuilder<T> proxyBuilder = proxyBuilder();
        return proxyBuilder.url(serviceName).type(type).build();
    }

    @Override
    public <T> ClientProxyBuilder<T> proxyBuilder() {
        return new ProxyBuilder<>(context);
    }

    private static class ProxyBuilder<T> implements ClientProxyBuilder<T> {

        private String url;
        private Class<T> type;

        private ApplicationContext context;

        ProxyBuilder(ApplicationContext context) {
            this.context = context;
        }

        @Override
        public ClientProxyBuilder<T> type(Class<T> type) {
            this.type = type;
            return this;
        }

        @Override
        public ClientProxyBuilder<T> url(String url) {
            this.url = url;
            return this;
        }

        @Override
        public T build() throws Exception {
            JAXRSClientFactoryBean bean = new JAXRSClientFactoryBean();

            Bus bus = context.getBean("cxf", Bus.class);
            if (bus != null) {
                bean.setBus(bus);
            }

            bean.setAddress(url);
            bean.setServiceClass(type);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            List<Object> providerList = new ArrayList<>();

            providerList.add(new JacksonJsonProvider(objectMapper));
            bean.setProviders(providerList);

            bean.getInFaultInterceptors().add(new LoggingInInterceptor());
            bean.getOutFaultInterceptors().add(new LoggingOutInterceptor());

            return (T) bean.create();
        }
    }
}
