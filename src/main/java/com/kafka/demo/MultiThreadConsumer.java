package com.kafka.demo;

import com.kafka.other.ConsumerLoop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * @author wanchen.chen
 * @ClassName MultiThreadConsumer
 * @Despriction: 多线程消费kafka主题多个分区示例
 * @date 2019/6/12 15:12
 * @Version 1.0
 */
public class MultiThreadConsumer {

    public static void main(String[] args) {
        String topic="book";
        moreLoopConsumer(topic);
    }
    /**
    * @author wanchen.chen
    * @Description 多线程运行示例
    * @Date 15:28 2019/6/12
    * @Param [topic]
    * @return void
    **/
    public static void moreLoopConsumer(String topic){
        int numConsumers = 3;
        //主题
        List<String> topics = Arrays.asList(topic);
        //开启线程池
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);
        final List<ConsumerLoop> consumers = new ArrayList<>();
        for (int i = 0; i < numConsumers; i++) {
            ConsumerLoop consumer = new ConsumerLoop(i, topics);
            consumers.add(consumer);
            executor.submit(consumer);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (ConsumerLoop consumer : consumers) {
                    consumer.shutdown();
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
