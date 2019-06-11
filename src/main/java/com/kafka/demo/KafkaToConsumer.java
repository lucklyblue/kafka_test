package com.kafka.demo;

import com.kafka.utils.PropertiesUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

/**
 * @author wanchen.chen
 * @ClassName KafkaToConsumer
 * @Despriction: kafka消费者
 * @date 2019/6/10 11:55
 * @Version 1.0
 */
public class KafkaToConsumer {
    //初始化参数
    private static Consumer<String, String> consumer = new KafkaConsumer<String, String>(PropertiesUtil.getProperties("/consumer.properties"));

    /**
    * @author wanchen.chen
    * @Description 消费组测试
    * @Date 15:03 2019/6/10
    * @Param [args]
    * @return void
    **/
    public static void main(String[] args) {
        String topic ="book";//主题
        getKafkaData(topic);
    }

    /**
    * @author wanchen.chen
    * @Description 从kafka消费数据
    * @Date 9:29 2019/6/11
    * @Param [topic]
    * @return void
    **/
    public static void getKafkaData(String topic){
        Collection<String> topics = Arrays.asList(topic);
        //订阅主题
        consumer.subscribe(topics);
        ConsumerRecords<String,String> consumerRecords = null;
        while(true){
            //接下来就要从topic中拉去数据,超时时间为1s
            consumerRecords = consumer.poll(1000);
            //遍历每一条记录
            for(ConsumerRecord<String, String> consumerRecord : consumerRecords){
//                long offset = consumerRecord.offset();//获取偏移值
//                int partition = consumerRecord.partition();//获取分区号
//                Object key = consumerRecord.key();//获取key
//                Object value = consumerRecord.value();//获取值
                System.out.printf("offset = %d, key = %s, value = %s%n", consumerRecord.offset(), consumerRecord.key(), consumerRecord.value());
            }
            consumer.close();
            consumer.unsubscribe();
        }
    }

    /**
    * @author wanchen.chen
    * @Description 如果consumer在获得数据后需要加入处理，数据完毕后才确认offset，需要程序来控制offset的确认。
     * 举个例子：consumer获得数据后，需要将数据持久化到DB中。自动确认offset的情况下，如果数据从kafka集群读出，就确认，
     * 但是持久化过程失败，就会导致数据丢失。我们就需要控制offset的确认。
    * @Date 9:30 2019/6/11
    * @Param [topic]
    * @return void
    **/
    public static void getKafkaToDB(String topic){
        //为演示不一样的配置这里重新定义Properties，建议在.properties文件中配置
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.200.100:9092,192.168.200.101:9092,192.168.200.102:9092");
        props.put("group.id", "test");
        /* 关闭自动确认选项 */
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("foo", "bar"));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
             /* 数据达到批量要求，就写入DB，同步确认offset */
            if (buffer.size() >= minBatchSize) {
//                insertIntoDb(buffer);//这里是将数据写入数据库的操作，写入后再提交，保证事务性
                consumer.commitSync();
                buffer.clear();
            }
        }

    }


}
