package com.deepak.wr.platform.kafka.impl;

import com.deepak.wr.platform.Serializer;
import com.deepak.wr.platform.conf.Kafka;
import com.deepak.wr.platform.kafka.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by deepakc on 10/04/17.
 */
public class DefaultConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConsumer.class);

    private Serializer serializer;
    private Kafka kafkaConf;
    private Consumer consumer;
    private String topic;

    org.apache.kafka.clients.consumer.KafkaConsumer kafkaConsumer;

    public DefaultConsumer(String topic, Serializer serializer, Consumer consumer, Kafka kafkaConf) {
        this.serializer = serializer;
        this.consumer = consumer;
        this.kafkaConf = kafkaConf;
        this.topic = topic;
        init();
    }

    private void init() {
        Properties props = new Properties();
        props.putAll(kafkaConf.getExtraProps());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConf.quorumToCsv());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConf.getConsumerGroup());
        kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer(props);

        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            kafkaConsumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(Long.MAX_VALUE);
                try {
                    for (ConsumerRecord<String, byte[]> record : records) {
                        Object o = serializer.deserialize(record.value());
                        consumer.consume(o);
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            kafkaConsumer.close();
        }
    }

    public void shutdown() {
        kafkaConsumer.wakeup();
    }
}
