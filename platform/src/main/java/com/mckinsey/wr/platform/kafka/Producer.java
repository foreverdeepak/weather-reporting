package com.deepak.wr.platform.kafka;

/**
 * Created by deepakc on 10/04/17.
 */
public interface Producer {

    void produce(String key, Object o) throws PubsubException;
    void produce(Object o) throws PubsubException;

}
