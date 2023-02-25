package com.github.r21.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {
    void consume(ConsumerRecord<String, T> record) throws Exception;
}
