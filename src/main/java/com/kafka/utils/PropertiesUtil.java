package com.kafka.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author cwc
 * @Despriction: 读取properties文件工具类
 * @date 2018/9/12 15:38
 */
public class PropertiesUtil {
    /**
     * 获取properties中的配置
     * @param properPath
     * @return
     */
    public static Properties getProperties(String properPath){
        Properties properties =new Properties();//新建Properties类
        InputStream is =PropertiesUtil.class.getResourceAsStream(properPath);//获取数据
        InputStreamReader isr=null;
        try {
            isr=new InputStreamReader(is,"UTF-8");//转换编码
            properties.load(isr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }


}
