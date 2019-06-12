package com.kafka.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author wanchen.chen
 * @ClassName FileMonitoring
 * @Despriction: 用于监控文件夹下新产生的数据
 * @date 2019/6/11 14:37
 * @Version 1.0
 */
public class FileMonitoring {
    public static void main(String[] args) throws Exception{
        File directory = new File("D:\\tmp\\TXT");
        // 轮询间隔 5 秒
        long interval = TimeUnit.SECONDS.toMillis(5);
        // 创建一个文件观察器用于处理文件的格式
        FileAlterationObserver observer = new FileAlterationObserver(directory, FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".txt")));
        //设置文件变化监听器
        observer.addListener(new MyFileListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval,observer);
        monitor.start();
        //Thread.sleep(30000);
        //monitor.stop();
    }
}
/**
* @author wanchen.chen
* @Description 监控文件夹下的数据
* @Date 14:48 2019/6/11
* @Param 
* @return 
**/
final class MyFileListener implements FileAlterationListener {
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        System.out.println("monitor start scan files..");
    }
    @Override
    public void onDirectoryCreate(File file) {
        System.out.println(file.getName()+" director created.");
    }
    @Override
    public void onDirectoryChange(File file) {
        System.out.println(file.getName()+" director changed.");
    }
    @Override
    public void onDirectoryDelete(File file) {
        System.out.println(file.getName()+" director deleted.");
    }
    @Override
    /**
    * @author wanchen.chen
    * @Description 当文件创建时触发操作
    * @Date 16:02 2019/6/11
    * @Param [file]
    * @return void
    **/
    public void onFileCreate(File file) {
        System.out.println("捕获文件：-----"+file.getName());
        //日期
        String str=file.getName().substring(0,8);
        String tm=str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
        //txt文件
        String st=file.getName().substring(0,24);
        String cod="sh /keduox/hive_data/hida.sh /root/timedata/"+st+" "+tm;
        try {
            Process process=Runtime.getRuntime().exec(cod);
            System.out.println("错误信息："+ IOUtils.toString(process.getErrorStream()));
            System.out.println("正常执行："+ IOUtils.toString(process.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getName()+" created.");
    }
    @Override
    public void onFileChange(File file) {
        System.out.println(file.getName()+" changed.");
    }
    @Override
    public void onFileDelete(File file) {
        System.out.println(file.getName()+" deleted.");
    }
    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
        System.out.println("monitor stop scanning..");
    }
}
