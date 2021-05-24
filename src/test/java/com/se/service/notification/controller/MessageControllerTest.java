package com.se.service.notification.controller;



import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.entity.NotificationGroup;
import com.se.service.notification.dao.entity.TemplateVariable;
import com.se.service.notification.dao.repository.NotificationRepository;
import com.se.service.notification.dao.repository.TemplateVariablesRepository;
import com.se.service.notification.model.enums.NotificationAlertType;
import com.se.service.notification.model.request.DestinationAddressAlertType;
import com.se.service.notification.model.request.SendNotificationRequest;
import com.se.service.notification.service.impl.TemplateAttributeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by Evgeniy Skiba
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnSuccess() throws Exception {

        //Template placeholders block
        TemplateVariable templateVariable1 = new TemplateVariable("attr1");
        TemplateVariable templateVariable2 = new TemplateVariable("attr2");

        List<TemplateVariable> templateVariableList = Arrays.asList(templateVariable1,templateVariable2);

        TemplateVariablesRepository localTemplateVariablesRepository =
                Mockito.mock(TemplateVariablesRepository.class);

        Mockito.when(localTemplateVariablesRepository.findAll()).
                thenReturn(templateVariableList);

        // Notification group block
        NotificationGroup notificationGroup = new NotificationGroup();
        notificationGroup.setEnabled(true);
        notificationGroup.setName("Notification group name");

        Notification notification = new Notification();
        notification.setEnabled(true);
        notification.setHtmlPart("<head><style>body {background-color: powderblue;}h1 {color: blue;}p {color: red;}</style></head> <body> <p>Password</p> <p>${attr1}</p><p> Some attribute 2: ${attr2} </p> <p>Regards, SE</p></body> </html>");
        notification.setName("notification_name");

        SendNotificationRequest notificationRequest = new SendNotificationRequest();

        DestinationAddressAlertType destinationAddressAlertType =
                new DestinationAddressAlertType();

        destinationAddressAlertType.setAlertType(NotificationAlertType.EMAIL);
        destinationAddressAlertType.setDestinationAddress("test@mail.com");

        notificationRequest.setDestinationAddressList(Arrays.asList(destinationAddressAlertType));
        notificationRequest.setPlaceholdersMap(
                new HashMap<String, String>(){{
                    put("test","11111111111");
                }}
        );

        // mocked notification from db
        Optional<Notification> notificationOptional = Optional.of(notification);

        NotificationRepository localMockRepository = Mockito.mock(NotificationRepository.class);
        Mockito.when(localMockRepository.findById(notificationRequest.getNotificationId())).
                thenReturn(notificationOptional);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // TODO: bind all fields

        HttpEntity<String> request =
                new HttpEntity<String>(notificationRequest.toString(), headers);

        String postUrl = "/message/send";

        ResponseEntity<Object> response =  restTemplate.postForEntity(
                "http://localhost:" + port + "/"+postUrl,
        notificationRequest,
        Object.class);



        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), notNullValue());
    }

}