package com.cpw.kafka.message;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


public class MyProducer {


  public static void main(String[] args) throws InterruptedException {


    Properties props = new Properties();
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    props.put("metadata.broker.list", "10.20.71.161:9092,10.20.71.150:9092,10.20.70.19:9092");
    props.put("partitioner.class", MyPartitioner.class.getName());
    props.put("request.required.acks", "1");
    ProducerConfig config = new ProducerConfig(props);
    Producer<String, String> producer = new Producer<String, String>(config);


    // 批量发送
    List<KeyedMessage<String, String>> messages = new ArrayList<KeyedMessage<String, String>>(100);
    for (int i = 0; i <= 100; i++) {
      KeyedMessage<String, String> message =
          new KeyedMessage<String, String>(KafkaConstants.TOPIC, i + "", "Message" + i);
      messages.add(message);
      if (i % 100 == 0) {
        producer.send(messages);
        messages.clear();
      }
    }
    producer.send(messages);
  }
}