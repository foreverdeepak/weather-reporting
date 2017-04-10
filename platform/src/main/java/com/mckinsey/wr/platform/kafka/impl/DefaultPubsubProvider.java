package com.mckinsey.wr.platform.kafka.impl;

import com.mckinsey.wr.platform.Serializer;
import com.mckinsey.wr.platform.conf.Config;
import com.mckinsey.wr.platform.conf.Kafka;
import com.mckinsey.wr.platform.kafka.Consumer;
import com.mckinsey.wr.platform.kafka.Producer;
import com.mckinsey.wr.platform.kafka.PubsubProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;


/**
 * Created by deepakc on 10/04/17.
 */
public class DefaultPubsubProvider implements PubsubProvider {

    private Config config;
    private Map<String, DefaultProducer> producerMap = new HashMap<>();
    private Map<String, DefaultConsumer> consumerMap = new HashMap<>();

    private Semaphore semaphore = new Semaphore(1);

    public DefaultPubsubProvider(Config config) {
        this.config = config;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.shutdown();
        }));

    }

    private void shutdown() {
        for (DefaultProducer defaultProducer : producerMap.values()) {
            defaultProducer.stop();
        }

        for (DefaultConsumer defaultConsumer : consumerMap.values()) {
            defaultConsumer.shutdown();
        }
    }

    @Override
    public Producer provide(String id, String topic, Serializer<?> serializer) {
        try {
            Kafka kafka = config.getKafkaConfById(id);
            DefaultProducer defaultProducer = producerMap.get(id);
            if (defaultProducer == null) {
                semaphore.acquire();
                if (defaultProducer == null) {
                    defaultProducer = new DefaultProducer(topic, kafka, serializer);
                    producerMap.put(id, defaultProducer);
                }
                semaphore.release();
            }
            return defaultProducer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerConsumer(String id, String topic, Consumer<?> consumer, Serializer<?> serializer) {
        try {
            Kafka kafka = config.getKafkaConfById(id);
            DefaultConsumer defaultConsumer = consumerMap.get(id);
            if (defaultConsumer == null) {
                semaphore.acquire();
                if (defaultConsumer == null) {
                    defaultConsumer = new DefaultConsumer(topic, serializer, consumer, kafka);
                    consumerMap.put(id, defaultConsumer);
                }
                semaphore.release();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseConsumer(String id) {
        DefaultConsumer consumer = consumerMap.get(id);
        if (consumer != null) {
            consumer.shutdown();
        }
    }

    public void releaseProducer(String id) {
        DefaultProducer producer = producerMap.get(id);
        if (producer != null) {
            producer.stop();
        }
    }
}
