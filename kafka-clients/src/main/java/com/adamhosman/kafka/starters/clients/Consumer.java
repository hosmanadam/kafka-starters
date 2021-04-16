package com.adamhosman.kafka.starters.clients;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static com.adamhosman.kafka.starters.clients.Constants.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class Consumer {

    public static final Logger log = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(createConsumerProperties());
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                log.info(String.format("Received message with key '%s', value '%s', partition '%s', offset '%s'",
                        record.key(),
                        record.value(),
                        record.partition(),
                        record.offset())
                );
            }
        }
    }


    private static Properties createConsumerProperties() {
        Properties p = new Properties();
        p.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_IP);
        p.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.put(GROUP_ID_CONFIG, GROUP_NAME);
        p.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        return p;
    }

}
