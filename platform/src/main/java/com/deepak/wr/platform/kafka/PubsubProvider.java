package com.deepak.wr.platform.kafka;


import com.deepak.wr.platform.Serializer;

/**
 * Created by deepakc on 10/04/17.
 */
public interface PubsubProvider {

    Producer provide(String id, String topic, Serializer<?> serializer);

    void registerConsumer(String id, String topic, Consumer<?> consumer, Serializer<?> serializer);

}
