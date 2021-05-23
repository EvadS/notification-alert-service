package com.se.service.notification.controller;



import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.repository.NotificationRepository;
import com.se.service.notification.model.request.SendNotificationRequest;
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
    public void shouldReturnSucess() throws Exception {

        SendNotificationRequest notificationRequest = new SendNotificationRequest();

        // mocked notification from db
        Notification notification = new Notification();
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