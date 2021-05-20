package com.se.service.notification.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


/**
 * Created by Evgeniy Skiba
 */

@ConfigurationProperties(prefix = "discovery.sm")
@Validated
public class NotificationProperties {

    @NotBlank
    private String host;

    @Min(8000)
    private int port;


    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }



    @PostConstruct
    private  void init(){
        System.out.printf("host in props :"+ host);
    }

}
