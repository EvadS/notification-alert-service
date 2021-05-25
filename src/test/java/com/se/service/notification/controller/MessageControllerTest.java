package com.se.service.notification.controller;


import com.se.service.notification.NotificationConfiguration;
import com.se.service.notification.component.SendGridMailerComponent;
import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.entity.NotificationGroup;
import com.se.service.notification.dao.entity.TemplateVariable;
import com.se.service.notification.dao.repository.NotificationRepository;
import com.se.service.notification.dao.repository.TemplateVariablesRepository;
import com.se.service.notification.model.enums.NotificationAlertType;
import com.se.service.notification.model.request.DestinationAddressAlertType;
import com.se.service.notification.model.request.SendNotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.support.TestPropertySourceUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Evgeniy Skiba
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {


    public static final long DEFAULT_NOTIFICATION_ID = 1L;

    public static final long NOT_EXISTS_NOTIFICATION_ID = 1000L;

    public static final String DEFAULT_RECIPIENT = "evad.se.dev@gmail.com";
    public static final String postUrl = "/message/send";
    @MockBean
    TemplateVariablesRepository mockTemplateVariablesRepository;
    @MockBean
    SendGridMailerComponent sendGridMailerComponent;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private NotificationRepository mockNotificationRepository;

    @BeforeEach
    public void setUp() throws IOException {
        // Notification group block
        NotificationGroup notificationGroup = new NotificationGroup();
        notificationGroup.setId(1L);
        notificationGroup.setEnabled(true);
        notificationGroup.setName("Notification group name");

        Notification notification = new Notification();
        notification.setEnabled(true);
        notification.setId(DEFAULT_NOTIFICATION_ID);
        notification.setHtmlPart("<head><style>body {background-color: powderblue;}h1 {color: blue;}p {color: red;}</style></head> <body> <p>Password</p> <p>${attr1}</p><p> Some attribute 2: ${attr2} </p> <p>Regards, SE</p></body> </html>");
        notification.setName("notification_name");
        notification.setNotificationGroup(notificationGroup);

        Mockito.when(this.mockNotificationRepository.findById(DEFAULT_NOTIFICATION_ID))
                .thenReturn(Optional.of(notification));
        //for validator
        Mockito.when(this.mockNotificationRepository.existsById(DEFAULT_NOTIFICATION_ID))
                .thenReturn(true);

        TemplateVariable templateVariable1 = new TemplateVariable("attr1");
        TemplateVariable templateVariable2 = new TemplateVariable("attr2");

        List<TemplateVariable> templateVariableList = Arrays.asList(templateVariable1, templateVariable2);
        Mockito.when(mockTemplateVariablesRepository.findAll()).
                thenReturn(templateVariableList);

        Mockito.when(sendGridMailerComponent.sendHtml("recipient", "subject", "html"))
                .thenReturn(true);
    }


    @DisplayName("should work correct")
    @Test
    public void shouldReturnSuccess() {
        NotificationGroup notificationGroup = new NotificationGroup();
        notificationGroup.setEnabled(true);
        notificationGroup.setName("Notification group name");

        Notification notification = new Notification();
        notification.setEnabled(true);
        notification.setId(DEFAULT_NOTIFICATION_ID);
        notification.setHtmlPart("<head><style>body {background-color: powderblue;}h1 {color: blue;}p {color: red;}</style></head> <body> <p>Password</p> <p>${attr1}</p><p> Some attribute 2: ${attr2} </p> <p>Regards, SE</p></body> </html>");
        notification.setName("notification_name");
        notification.setNotificationGroup(notificationGroup);

        SendNotificationRequest notificationRequest = new SendNotificationRequest();
        notificationRequest.setNotificationId(DEFAULT_NOTIFICATION_ID);
        notificationRequest.setSubject("SUBJECT");

        DestinationAddressAlertType destinationAddressAlertType =
                new DestinationAddressAlertType();

        destinationAddressAlertType.setAlertType(NotificationAlertType.EMAIL);
        destinationAddressAlertType.setDestinationAddress(DEFAULT_RECIPIENT);

        notificationRequest.setDestinationAddressList(Arrays.asList(destinationAddressAlertType));
        notificationRequest.setPlaceholdersMap(
                new HashMap<String, String>() {{
                    put("attr1", "11111111111");
                    put("attr2", "Attribute value");
                }}
        );

        NotificationRepository localNotificationRepo = Mockito.mock(NotificationRepository.class);
        Mockito.when(localNotificationRepo.findById(DEFAULT_NOTIFICATION_ID)).thenReturn(Optional.of(notification));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                notificationRequest,
                Object.class);


        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));

    }

    @Test
    public void incorrect_request_model_return_bad_request() {

        NotificationGroup notificationGroup = new NotificationGroup();
        notificationGroup.setEnabled(true);
        notificationGroup.setName("Notification group name");

        Notification notification = new Notification();
        notification.setEnabled(true);
        notification.setId(NOT_EXISTS_NOTIFICATION_ID);
        notification.setHtmlPart("<head><style>body {background-color: powderblue;}h1 {color: blue;}p {color: red;}</style></head> <body> <p>Password</p> <p>${attr1}</p><p> Some attribute 2: ${attr2} </p> <p>Regards, SE</p></body> </html>");
        notification.setName("notification_name");
        notification.setNotificationGroup(notificationGroup);

        SendNotificationRequest notificationRequest = new SendNotificationRequest();
        notificationRequest.setNotificationId(NOT_EXISTS_NOTIFICATION_ID);
        notificationRequest.setSubject("SUBJECT");

        DestinationAddressAlertType destinationAddressAlertType =
                new DestinationAddressAlertType();

        destinationAddressAlertType.setAlertType(NotificationAlertType.EMAIL);
        destinationAddressAlertType.setDestinationAddress(DEFAULT_RECIPIENT);

        notificationRequest.setDestinationAddressList(Arrays.asList(destinationAddressAlertType));
        notificationRequest.setPlaceholdersMap(
                new HashMap<String, String>() {{
                    put("attr1", "11111111111");
                    put("attr2", "Attribute value");
                }}
        );

        NotificationRepository localNotificationRepo = Mockito.mock(NotificationRepository.class);
        Mockito.when(localNotificationRepo.findById(DEFAULT_NOTIFICATION_ID)).thenReturn(Optional.of(notification));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                notificationRequest,
                Object.class);


        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

    }

    @Test
    public void placeholder_not_found_should_return_bad_request() {
        NotificationGroup notificationGroup = new NotificationGroup();
        notificationGroup.setEnabled(true);
        notificationGroup.setName("Notification group name");

        Notification notification = new Notification();
        notification.setEnabled(true);
        notification.setId(DEFAULT_NOTIFICATION_ID);
        notification.setHtmlPart("<head><style>body {background-color: powderblue;}h1 {color: blue;}p {color: red;}</style></head> <body> <p>Password</p> <p>${attr1}</p><p> Some attribute 2: ${attr2} </p> <p>Regards, SE</p></body> </html>");
        notification.setName("notification_name");
        notification.setNotificationGroup(notificationGroup);

        SendNotificationRequest notificationRequest = new SendNotificationRequest();
        notificationRequest.setNotificationId(DEFAULT_NOTIFICATION_ID);
        notificationRequest.setSubject("SUBJECT");

        DestinationAddressAlertType destinationAddressAlertType =
                new DestinationAddressAlertType();

        destinationAddressAlertType.setAlertType(NotificationAlertType.EMAIL);
        destinationAddressAlertType.setDestinationAddress(DEFAULT_RECIPIENT);

        notificationRequest.setDestinationAddressList(Arrays.asList(destinationAddressAlertType));
        notificationRequest.setPlaceholdersMap(
                new HashMap<String, String>() {{
                    put("incorrect_attr1", "11111111111");
                    put("incorrect_attr2", "Attribute value");
                }}
        );

        NotificationRepository localNotificationRepo = Mockito.mock(NotificationRepository.class);
        Mockito.when(localNotificationRepo.findById(DEFAULT_NOTIFICATION_ID)).thenReturn(Optional.of(notification));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                notificationRequest,
                Object.class);


        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));


    }

    @Test
    public void notification_group_not_found_should_return_bad_request() {

    }


}