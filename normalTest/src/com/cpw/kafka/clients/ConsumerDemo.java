package com.cpw.kafka.clients;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import com.cpw.kafka.message.KafkaConstants;

/**
 * User: mzang Date: 2015-04-01 Time: 14:22
 */
public class ConsumerDemo {

	private Consumer<String, String> consumer;

	private void init() throws IOException {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.20.71.150:9092,10.20.71.161:9092,10.20.70.19:9092"); // See
																								// org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		// props.put("partition.assignment.strategy", "roundrobin");
		consumer = new KafkaConsumer<String, String>(props);
		TopicPartition partition0 = new TopicPartition(KafkaConstants.TOPIC, 0);
		TopicPartition partition1 = new TopicPartition(KafkaConstants.TOPIC, 1);
		TopicPartition partition2 = new TopicPartition(KafkaConstants.TOPIC, 2);
		consumer.assign(Arrays.asList(partition0, partition1));
	}

	public ConsumerDemo() throws IOException {
		init();
	}

	public ConsumerRecords<String, String> getMessages() {
		ConsumerRecords<String, String> ret = null;
		while (ret == null) {
			consumer.poll(10);
		}
		return ret;
	}

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.20.71.150:9092,10.20.71.161:9092,10.20.70.19:9092");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
	     consumer.subscribe(Arrays.asList(KafkaConstants.TOPIC));
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(1);
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
	     }
	}

}