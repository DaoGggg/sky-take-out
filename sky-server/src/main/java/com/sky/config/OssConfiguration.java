package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类 用于创建AliOssUtil对象
 */
//配置类注解 在项目启动时自动加载生成配置对象
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean //当没有才创建Bean对象
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云上传文件工具类对象AliOssUtil");
        return new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getBucketName());

    }
}
