package com.se.service.notification.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by Evgeniy Skiba
 */
@Configuration
@ConfigurationProperties(prefix = "amazon")
public class AwsConfiguration {

    Logger logger = LoggerFactory.getLogger(AwsConfiguration.class);
    private String accessKey;

    private String secretKey;

    private String regionStatic;

    public AwsConfiguration() {
    }

    @PostConstruct
    private void init(){
        int a =10;
        logger.info("===Region:" + regionStatic + ". secret"+ secretKey);

    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }


    public String getRegionStatic() {
        return regionStatic;
    }

    public void setRegionStatic(String regionStatic) {
        this.regionStatic = regionStatic;
    }
}
