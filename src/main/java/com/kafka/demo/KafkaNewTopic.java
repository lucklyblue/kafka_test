package com.kafka.demo;

import com.kafka.utils.PropertiesUtil;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * @author wanchen.chen
 * @ClassName KafkaNewTopic
 * @Despriction: kafka新版本通过IP:9092直接访问kafka修改主题，如果使用不了尝试旧版本的代码KafkaOldTopic。
 * @date 2019/6/12 10:47
 * @Version 1.0
 */
public class KafkaNewTopic {
    //获取kafka集群的连接
    private static final String brokerUrl = PropertiesUtil.getProperties("/produce.properties").getProperty("bootstrap.servers");
    //kafka新版本的管理类
    private static AdminClient adminClient;
    /**
    * @author wanchen.chen
    * @Description 使用无参构造器将参数传入adminClient
    * @Date 11:43 2019/6/12
    * @Param []
    **/
    public KafkaNewTopic(){
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,brokerUrl);
        this.adminClient=AdminClient.create(properties);
    }

    /**
    * @author wanchen.chen
    * @Description 测试main方法
    * @Date 11:44 2019/6/12
    * @Param [args]
    * @return void
    **/
    public static void main(String[] args) {
        String topic ="wan";
//        delTopics(topic);
//        createTopics(topic);
    }



    /**
    * @author wanchen.chen
    * @Description 新建topic
    * @Date 11:46 2019/6/12
    * @Param [topic]
    * @return void
    **/
    public static void createTopics(String topic) {
        //参数分别为：topic名称、分区数
        NewTopic newTopic = new NewTopic(topic,1, (short) 1);
        Collection<NewTopic> newTopicList = new ArrayList<>();
        newTopicList.add(newTopic);
        adminClient.createTopics(newTopicList);
        adminClient.close();
    }
    /**
    * @author wanchen.chen
    * @Description 删除主题
    * @Date 14:40 2019/6/12
    * @Param [topic]
    * @return void
    **/
    public static void delTopics(String topic){
        Collection<String> newTopicList = new ArrayList<>();
        newTopicList.add(topic);
        adminClient.deleteTopics(newTopicList);
        adminClient.close();
    }










}
