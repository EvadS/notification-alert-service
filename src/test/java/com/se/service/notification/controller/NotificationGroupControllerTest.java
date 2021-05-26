package com.se.service.notification.controller;

import com.se.service.notification.NotificationServiceApplication;
import com.se.service.notification.dao.entity.NotificationGroup;
import com.se.service.notification.dao.repository.NotificationGroupRepository;
import com.se.service.notification.model.request.NotificationGroupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Evgeniy Skiba
 */

//@WebMvcTest

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = NotificationServiceApplication.class)
class NotificationGroupControllerTest {

    public static final long DEFAULT_NOTIFICATION_ID = 1L;
    public static final String postUrl = "/notification-group";
    public static final String putUrl = "/notification-group";

//    @MockBean
//    private NotificationGroupRepository mockNotificationGroup;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private NotificationGroupRepository notificationGroupRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() throws IOException {
        // Notification group block
//        NotificationGroup notificationGroup = new NotificationGroup();
//        notificationGroup.setId(1L);
//        notificationGroup.setEnabled(true);
//        notificationGroup.setName("Notification group name");
//
//        Mockito.when(this.mockNotificationGroup.findById(DEFAULT_NOTIFICATION_ID))
//                .thenReturn(Optional.of(notificationGroup));
    }


    /// test autowired jpa is work correct
    @Test
    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {

        NotificationGroup notificationGroup = new NotificationGroup("test", true);
        NotificationGroup genericEntity = notificationGroupRepository.save(notificationGroup);

        Long generatedId = genericEntity.getId();
        NotificationGroup foundEntity = notificationGroupRepository.getOne(generatedId);

        assertNotNull(foundEntity);
        org.junit.jupiter.api.Assertions.assertEquals(genericEntity.getName(), foundEntity.getName());
    }

    @Test
    public void should_create_notification_group_success() {

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest("name", true);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                notificationRequest,
                Object.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void should_create_notification_group_validation_error() {
        NotificationGroupRequest notificationRequest = new NotificationGroupRequest();
        notificationRequest.setEnabled(false);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                notificationRequest,
                Object.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void create_notification_group_already_exists() {

        NotificationGroup notificationGroup = new NotificationGroup("test", true);
        notificationGroupRepository.save(notificationGroup);

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest();
        notificationRequest.setEnabled(false);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                notificationRequest,
                Object.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }


    @Test
    public void update_notification_group_success() {
        notificationGroupRepository.deleteAllInBatch();

        NotificationGroup notificationGroup = new NotificationGroup("test", true);
        NotificationGroup genericEntity = notificationGroupRepository.save(notificationGroup);

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest("test2", true);

        ResponseEntity<Object> response = restTemplate.exchange(
                "http://localhost:" + port + "/" + putUrl + "/" + genericEntity.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(notificationRequest),
                Object.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        notificationGroupRepository.deleteAllInBatch();
    }


    //Put mapping
    @Test
    public void update_notification_group_not_success() {
        notificationGroupRepository.deleteAllInBatch();

        NotificationGroup notificationGroup = new NotificationGroup("test", true);
        NotificationGroup genericEntity = notificationGroupRepository.save(notificationGroup);

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest();
        notificationRequest.setEnabled(false);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> response = restTemplate.exchange(
                "http://localhost:" + port + "/" + putUrl + "/" + genericEntity.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(notificationRequest),
                Object.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

        notificationGroupRepository.deleteAllInBatch();

    }

    @Test
    public void update_notification_group_not_found() {
        Long incorrectID = 1000L;
        NotificationGroup notificationGroup = new NotificationGroup("test", true);
        NotificationGroup genericEntity = notificationGroupRepository.save(notificationGroup);

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest("test2", true);

        ResponseEntity<Object> response = restTemplate.exchange(
                "http://localhost:" + port + "/" + putUrl + "/" + incorrectID,
                HttpMethod.PUT,
                new HttpEntity<>(notificationRequest),
                Object.class);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));

        notificationGroupRepository.deleteAllInBatch();
    }



    //Delete mapping
    @Test
    public void delete_notification_group_success() {

    }

    @Test
    public void delete_notification_group_not_found() {

    }
}