package com.kafka.demo;

import com.kafka.utils.PropertiesUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Collection;
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
        }
    }
}
