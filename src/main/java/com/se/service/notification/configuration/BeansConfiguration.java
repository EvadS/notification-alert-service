package com.se.service.notification.configuration;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * Created by Evgeniy Skiba
 */
@Configuration
public class BeansConfiguration {

    // TODO: move to props file
    @Value("${amazon.access.key}")
    private String amazonAccessKey;
    @Value("${amazon.secret.key}")
    private String amazonSecretKey;
//    @Value("${amazon.region}")
//    private String amazonRegion;


    @Bean
    public AmazonSNSClient awsSnsClient(){
        // TODO: use builder
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(amazonAccessKey, amazonSecretKey));

        AWSCredentials credentials = awsStaticCredentialsProvider.getCredentials();
        return new AmazonSNSClient(credentials);
    }
}