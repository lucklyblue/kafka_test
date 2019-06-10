package com.kafka.demo;

import com.kafka.utils.PropertiesUtil;
import org.apache.kafka.clients.producer.*;
/**
 * @author wanchen.chen
 * @ClassName KafkaToProduce
 * @Despriction: kafka发送消息demo
 * @date 2019/6/6 11:54
 * @Version 1.0
 */
public class KafkaToProduce {
    //初始化参数
    private static Producer<String,String> producer =new KafkaProducer<String, String>(PropertiesUtil.getProperties("/produce.properties"));

    /**
    * @author wanchen.chen
    * @Description 测试
    * @Date 14:21 2019/6/10
    * @Param [args]
    * @return void
    **/
    public static void main(String[] args) {
        String topic="book";//主题
        putAsynDataToKafka(topic);
//        putSyncDataToKafka(topic);
    }

    /**
     * @author wanchen.chen
     * @Description 异步传输数据到kafka，当kafka 主题有多个分区时无序，仅有一个分区时有序。
     * @Date 14:08 2019/6/6
     * @Param []
     * @return void
     **/
    public static void putAsynDataToKafka(String topic){
        for (int i=0;i<100;i++){
            //这里将元数据传入ProducerRecord
            ProducerRecord<String,String> record = new ProducerRecord<String,String>(topic, Integer.toString(i), "今天是9月："+i+"号");
            producer.send(record,
                    //反馈信息
                    new Callback() {
                        public void onCompletion(RecordMetadata metadata, Exception e) {
                            if(e != null) {
                                System.out.println("发送失败！");
                                System.out.println("The offset of the record we just sent is: " + metadata.offset());
                                e.printStackTrace();
                            } else {
                                System.out.println("发送成功！");
                            }
                        }
                    });
        }
        //将数据刷新到kafka
        producer.flush();
        //关闭连接
        producer.close();
    }

    /**
     * @author wanchen.chen
     * @Description 同步传输数据到kafka
     * @Date 14:08 2019/6/6
     * @Param []
     * @return void
     **/
    public static void putSyncDataToKafka(String topic){
        for (int i=0;i<100;i++){
            //这里将元数据传入ProducerRecord
            producer.send(new ProducerRecord<String, String>(topic, "hello", "hello meitu"+i));
        }
        //将数据刷新到kafka
        producer.flush();
        //关闭连接
        producer.close();
    }

}
