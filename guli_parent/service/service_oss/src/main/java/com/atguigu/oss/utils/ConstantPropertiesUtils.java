package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
// 当项目已启动 spring 接口 spring 加载之后 执行接口一个方法
@Component
public class ConstantPropertiesUtils implements InitializingBean {
//    读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

//    定义公开静态常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID=this.keyid;
        KEY_SECRET=this.keysecret;
        END_POINT=this.endpoint;
        BUCKET_NAME=this.bucketname;

    }
}
