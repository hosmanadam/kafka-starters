package com.adamhosman.kafkaplayground;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class Producer {

    public static final String TOPIC_1 = "topic_1";
    public static final Callback CALLBACK = createCallback();
    public static final Logger log = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(createProducerProperties());
        for (int i = 1; i < 20; i++) {
            producer.send(
                    new ProducerRecord<String, String>(
                            TOPIC_1,
                            Integer.toString(i % 2), // Send odd vs. even to different partitions
                            String.format("Sending message no. %s", i)
                    ),
                    CALLBACK);
        }
        producer.flush();
        producer.close();
    }

    private static Callback createCallback() {
        return new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    log.info(String.format("Topic: %s", recordMetadata.topic()));
                    log.info(String.format("Partition: %s", recordMetadata.partition()));
                    log.info(String.format("Offset: %s", recordMetadata.offset()));
                    log.info(String.format("Timestamp: %s", recordMetadata.timestamp()));
                } else {
                    log.error("No luck :(", e);
                }
            }
        };
    }

    private static Properties createProducerProperties() {
        Properties p = new Properties();
        p.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        p.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        p.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return p;
    }

}