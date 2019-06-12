package com.kafka.demo;

import com.kafka.utils.PropertiesUtil;
//import kafka.admin.AdminUtils;
//import kafka.admin.RackAwareMode;
//import kafka.server.ConfigType;
//import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author wanchen.chen
 * @ClassName KafkaTopic
 * @Despriction: java操作kafka主题,该代码应用于老版本的kafka，使用zookeeper进行连接，如果新版本的代码用不了可以用下这个代码连接
 *    要使用该代码需要导入包：<groupId>org.apache.kafka</groupId> <artifactId>kafka_2.12</artifactId> 版本根据集群版本选择
 *    推荐使用新版本的管理类AdminClient，具体查看本项目KafkaNewTopic
 * @date 2019/6/11 17:53
 * @Version 1.0
 */
public class KafkaOldTopic {
    //初始化
    private static final Properties pr = PropertiesUtil.getProperties("/zk.properties");
    /**
    * @author wanchen.chen
    * @Description //ZkUtils已经显示过时，以后的版本中将被删除,参数分别为：zookeeper集群IP端口，会话超时时间，连接超时时间
    * @Date 10:55 2019/6/12
    **/
//    private static ZkUtils zkUtils = ZkUtils.apply(pr.getProperty("zkUrl"), 30000, 30000, JaasUtils.isZkSecurityEnabled());
//
//    public static void main(String[] args) {
//        String topic="wan";
//       createTopic(topic);
//        selectTopic(topic);
//        updateTopic("wan");
//    }
//
//
//    /**
//    * @author wanchen.chen
//    * @Description 创建主题
//    * @Date 10:28 2019/6/12
//    * @Param [topic]
//    * @return void
//    **/
//    public static void createTopic(String topic){
//        //参数分别为：zk连接，主题名，分数数，副本数，剩余两个不用管
//        AdminUtils.createTopic(zkUtils, topic, 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
//        zkUtils.close();
//    }
//    /**
//    * @author wanchen.chen
//    * @Description 删除主题
//    * @Date 10:41 2019/6/12
//    * @Param [topic]
//    * @return void
//    **/
//    public static void delTopic(String topic){
//        AdminUtils.deleteTopic(zkUtils, topic);
//        zkUtils.close();
//    }
//
//    /**
//    * @author wanchen.chen
//    * @Description 查看topic属性
//    * @Date 10:45 2019/6/12
//    * @Param [topic]
//    * @return void
//    **/
//    public static void selectTopic(String topic){
//        // 获取topic属性
//        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topic);
//        // 查询topic-level属性
//        Iterator it = props.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry entry=(Map.Entry)it.next();
//            Object key = entry.getKey();
//            Object value = entry.getValue();
//            System.out.println(key + " = " + value);
//        }
//        zkUtils.close();
//    }
//
//    /**
//    * @author wanchen.chen
//    * @Description 修改主题属性
//    * @Date 10:42 2019/6/12
//    * @Param [oldTopic, newTopic]
//    * @return void
//    **/
//    public static void updateTopic(String topic){
//        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topic);
//        // 增加topic级别属性
//        props.put("min.cleanable.dirty.ratio", "0.3");
//        // 删除topic级别属性
//        props.remove("max.message.bytes");
//        // 修改topic 'test'的属性
//        AdminUtils.changeTopicConfig(zkUtils, topic, props);
//        zkUtils.close();
//    }
}
