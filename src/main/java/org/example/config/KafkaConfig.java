package org.example.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap.server}")
    protected String servers;

    @Value("${spring.kafka.consumer.group-id}")
    protected String groupId;
    @Value("${spring.kafka.topic.accounts}")
    protected String account;
    @Value("${spring.kafka.topic.metrics}")
    protected String metrics;
    @Value("${spring.kafka.session.timeout.ms:45000}")
    protected String sessionTimeout;
    @Value("${spring.kafka.max.partition.fetch.bytes:300000}")
    protected String maxPartitionFetchBytes;
    @Value("${spring.kafka.max.poll.records:1}")
    protected String maxPollRecords;
    @Value("${spring.kafka.max.poll.interval.ms:300000}")
    protected String maxPollIntervalsMs;
    @Value("${spring.kafka.consumer.heartbeat.interval}")
    protected String heartbeatInterval;



}
