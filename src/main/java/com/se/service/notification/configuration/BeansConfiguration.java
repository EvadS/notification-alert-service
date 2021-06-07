package com.se.service.notification.configuration;

import com.amazonaws.auth.*;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by Evgeniy Skiba
 */
@Configuration
public class BeansConfiguration {

    private final  AwsConfiguration awsConfiguration;

    public BeansConfiguration(AwsConfiguration awsConfiguration) {
        this.awsConfiguration = awsConfiguration;
    }

    @Bean
    public AmazonSNSClient awsSnsClient(){

        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsConfiguration.getAccessKey(), awsConfiguration.getSecretKey()));

        AWSCredentials credentials = awsStaticCredentialsProvider.getCredentials();
        return new AmazonSNSClient(credentials);
    }
}