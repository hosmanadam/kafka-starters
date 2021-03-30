package com.adamhosman.kafkaplayground;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class Producer {

    public static final String TOPIC_1 = "topic_1";

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(createProducerProperties());
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_1, "Hello world!");
        producer.send(record);
        producer.flush();
        producer.close();
    }

    private static Properties createProducerProperties() {
        Properties p = new Properties();
        p.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        p.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        p.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return p;
    }

}
