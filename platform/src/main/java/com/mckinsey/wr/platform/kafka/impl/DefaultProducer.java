package com.mckinsey.wr.platform.kafka.impl;

import com.mckinsey.wr.platform.Serializer;
import com.mckinsey.wr.platform.conf.Kafka;
import com.mckinsey.wr.platform.kafka.Producer;
import com.mckinsey.wr.platform.kafka.PubsubException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by deepakc on 10/04/17.
 */
public class DefaultProducer implements Producer {

    private KafkaProducer kafkaProducer;
    private Serializer serializer;
    private Kafka kafkaConf;
    private String topic;

    public DefaultProducer(String topic, Kafka kafkaConf, Serializer serializer) {
        this.serializer = serializer;
        this.kafkaConf = kafkaConf;
        this.topic = topic;
        init();
    }

    private void init() {
        Properties props = new Properties();
        props.putAll(kafkaConf.getExtraProps());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConf.quorumToCsv());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        kafkaProducer = new KafkaProducer(props);
    }

    @Override
    public void produce(String key, Object o) throws PubsubException {
        byte[] bytes = serializer.serialize(o);
        ProducerRecord producerRecord;
        if (key != null) {
            producerRecord = new ProducerRecord(topic, key, bytes);
        } else {
            producerRecord = new ProducerRecord(topic, bytes);
        }

        try {
            kafkaProducer.send(producerRecord).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new PubsubException(e.getMessage(), e);
        }
    }

    @Override
    public void produce(Object o) throws PubsubException {
        this.produce(null, o);
    }

    public void stop() {
        kafkaProducer.close();
    }
}
