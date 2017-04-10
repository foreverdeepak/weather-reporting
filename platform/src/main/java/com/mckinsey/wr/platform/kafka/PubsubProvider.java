package com.mckinsey.wr.platform.kafka;


import com.mckinsey.wr.platform.Serializer;

/**
 * Created by deepakc on 10/04/17.
 */
public interface PubsubProvider {

    Producer provide(String id, String topic, Serializer<?> serializer);

    void registerConsumer(String id, String topic, Consumer<?> consumer, Serializer<?> serializer);

}
