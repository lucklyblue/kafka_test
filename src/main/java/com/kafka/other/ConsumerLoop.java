package com.kafka.other;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kafka.demo.KafkaToConsumer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
/**
 * @author wanchen.chen
 * @ClassName ConsumerLoop
 * @Despriction: 多线程消费者实现类
 * @date 2019/6/12 15:10
 * @Version 1.0
 */
public class ConsumerLoop implements Runnable {

    //初始化属性
    private final Consumer<String, String> consumer;
    private final List<String> topics;
    private final int id;

    public ConsumerLoop(int id, List<String> topics) {
        this.id = id;
        this.topics = topics;
        this.consumer = KafkaToConsumer.consumer;
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(topics);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, String> record : records) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    System.out.println(this.id + ": " + data);
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            consumer.close();
        }
    }
    public void shutdown() {
        consumer.wakeup();
    }
}

