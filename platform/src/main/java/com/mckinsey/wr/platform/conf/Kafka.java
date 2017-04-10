package com.mckinsey.wr.platform.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by deepakc on 10/04/17.
 */
public class Kafka {

    private String id;

    @JsonProperty("zookeeper-quorum")
    private String[] zookeeperQuorum;

    @JsonProperty("kafka-brokers")
    private String[] kafkaBrokers;

    @JsonProperty("consumer-group")
    private String consumerGroup;

    @JsonProperty("extra-properties")
    private Map<String, Object> extraProps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getZookeeperQuorum() {
        return zookeeperQuorum;
    }

    public void setZookeeperQuorum(String[] zookeeperQuorum) {
        this.zookeeperQuorum = zookeeperQuorum;
    }

    public String[] getKafkaBrokers() {
        return kafkaBrokers;
    }

    public void setKafkaBrokers(String[] kafkaBrokers) {
        this.kafkaBrokers = kafkaBrokers;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public Map<String, Object> getExtraProps() {
        return extraProps;
    }

    public void setExtraProps(Map<String, Object> extraProps) {
        this.extraProps = extraProps;
    }

    public String quorumToCsv() {
        return StringUtils.arrayToCommaDelimitedString(zookeeperQuorum);
    }

    public String brokersToCsv() {
        return StringUtils.arrayToCommaDelimitedString(kafkaBrokers);
    }

}
