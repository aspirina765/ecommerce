package com.github.r21.ecommerce;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

class KafkaService<T> implements Closeable {
    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;

    KafkaService(String groupId, String topic, ConsumerFunction parse, Class<T> type, Map<String,String> properties) {
        this(parse, groupId, type, properties);
        consumer.subscribe(Collections.singletonList(topic));
    }

    KafkaService(String groupId, Pattern topic, ConsumerFunction parse, Class<T> type, Map<String,String> properties) {
        this(parse, groupId, type, properties);
        consumer.subscribe(topic);
    }

    private KafkaService(ConsumerFunction parse, String groupId, Class<T> type, Map<String, String> properties) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(getProperties(type, groupId, properties));
    }

    void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) {
                    try {
                        parse.consume(record);
                    } catch (Exception e) {
                        // only catches Exception because no matter which Exception
                        // i want to recover and parse the next one
                        // so far, just logging the exception for this message
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Properties getProperties(Class<T> type, String groupId, Map<String, String> overrideProperties) {
        var properties = new Properties();
//        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "cluster.playground.cdkt.io:9092");
        properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        properties.setProperty(SaslConfigs.SASL_MECHANISM, "PLAIN");
        properties.setProperty(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username='2keur0EP8I5M136uGQWzwX' password='eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGguY29uZHVrdG9yLmlvIiwic291cmNlQXBwbGljYXRpb24iOiJhZG1pbiIsInVzZXJNYWlsIjpudWxsLCJwYXlsb2FkIjp7InZhbGlkRm9yVXNlcm5hbWUiOiIya2V1cjBFUDhJNU0xMzZ1R1FXendYIiwib3JnYW5pemF0aW9uSWQiOjY3Mjg5LCJ1c2VySWQiOjc3MjEzLCJmb3JFeHBpcmF0aW9uQ2hlY2siOiI3NDQzOWViOC00ZWNkLTQ3NGQtYWU4Ni0wOGFiMjczNGM4NDAifX0.MpoegseRRMvZygbnIDFU_-mEdrF6wt5RAXWjDdUi7ig';");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        properties.putAll(overrideProperties);
        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
