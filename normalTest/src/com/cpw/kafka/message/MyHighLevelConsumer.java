package com.cpw.kafka.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class MyHighLevelConsumer {

	/**
	 * 该consumer所属的组ID
	 */
	private String groupid;

	/**
	 * 该consumer的ID
	 */
	private String consumerid;

	/**
	 * 每个topic开几个线程？
	 */
	private int threadPerTopic;

	public MyHighLevelConsumer(String groupid, String consumerid, int threadPerTopic) {
		super();
		this.groupid = groupid;
		this.consumerid = consumerid;
		this.threadPerTopic = threadPerTopic;
	}

	public void consume() {
		Properties props = new Properties();
		props.put("group.id", groupid);
		props.put("zookeeper.connect", "10.20.71.150:2181,10.20.70.19:2181,10.20.71.161:2181");
//		props.put("zookeeper.connect", "localhost:2181");    
//        props.put("zookeeper.connectiontimeout.ms", "30000");    
        props.put("zookeeper.session.timeout.ms", "5990");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000"); 
        props.put("auto.offset.reset", "smallest");
        
        props.put("rebalance.max.retries", "5");
        props.put("rebalance.backoff.ms", "1200");
		// props.put("auto.commit.interval.ms", "1000");

		ConsumerConfig config = new ConsumerConfig(props);
		ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();

		// 设置每个topic开几个线程
		topicCountMap.put(KafkaConstants.TOPIC, threadPerTopic);

		// 获取stream
		Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(topicCountMap);

		 ExecutorService executor = Executors.newFixedThreadPool(threadPerTopic);
		// 为每个stream启动一个线程消费消息
		for (KafkaStream<byte[], byte[]> stream : streams.get(KafkaConstants.TOPIC)) {
			executor.execute(new MyStreamThread(stream));
			
		}
	}

	/**
	 * 每个consumer的内部线程
	 * 
	 * @author cuilei05
	 *
	 */
	private class MyStreamThread extends Thread {
		private KafkaStream<byte[], byte[]> stream;

		public MyStreamThread(KafkaStream<byte[], byte[]> stream) {
			super();
			this.stream = stream;
		}

		@Override
		public void run() {
			
				ConsumerIterator<byte[], byte[]> streamIterator = stream.iterator();
				int count = 0;
				// 逐条处理消息
				while (streamIterator.hasNext()) {
					MessageAndMetadata<byte[], byte[]> message = streamIterator.next();
					try{
					String topic = message.topic();
					int partition = message.partition();
					long offset = message.offset();
					String key = new String(message.key());
					String msg = new String(message.message());
					// 在这里处理消息,这里仅简单的输出
					// 如果消息消费失败，可以将已上信息打印到日志中，活着发送到报警短信和邮件中，以便后续处理
					System.out.println("groupId" + groupid + "consumerid:" + consumerid + ", thread : "
							+ Thread.currentThread().getName() + ", topic : " + topic + ", partition : " + partition
							+ ", offset : " + offset + " , key : " + key + " , mess : " + msg);
					if(count==3)
						throw new RuntimeException("error!!!");
					count++;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			
			
		}
	}

	public static void main(String[] args) {
		String groupid = "myconsumergroup";
		final MyHighLevelConsumer consumer1 = new MyHighLevelConsumer(groupid, "myconsumer1", 2);
		final MyHighLevelConsumer consumer2 = new MyHighLevelConsumer(groupid, "myconsumer2", 2);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					consumer2.consume();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					consumer1.consume();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();
		
		
		
	}
}
