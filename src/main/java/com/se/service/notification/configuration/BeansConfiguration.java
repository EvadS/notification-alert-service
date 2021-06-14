package com.se.service.notification.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by Evgeniy Skiba
 */
@Configuration
public class BeansConfiguration {

    private final AwsConfiguration awsConfiguration;

    public BeansConfiguration(AwsConfiguration awsConfiguration) {
        this.awsConfiguration = awsConfiguration;
    }

    @Bean
    public AmazonSNSClient awsSnsClient() {

        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsConfiguration.getAccessKey(), awsConfiguration.getSecretKey()));

        AWSCredentials credentials = awsStaticCredentialsProvider.getCredentials();
        return new AmazonSNSClient(credentials);
    }

    @Bean
    public AmazonSimpleEmailService awsSes() {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        awsConfiguration.getAccessKey(),
                                        awsConfiguration.getSecretKey()
                                )))
                .withRegion(Regions.fromName(awsConfiguration.getRegionStatic()))
                .build();
    }
}