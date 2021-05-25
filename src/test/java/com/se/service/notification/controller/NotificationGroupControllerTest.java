package com.se.service.notification.controller;

import com.se.service.notification.dao.entity.NotificationGroup;
import com.se.service.notification.dao.repository.NotificationGroupRepository;
import com.se.service.notification.dao.repository.NotificationRepository;
import com.se.service.notification.model.request.NotificationGroupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Evgeniy Skiba
 */


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationGroupControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private NotificationGroupRepository mockNotificationGroup;

    public static final long DEFAULT_NOTIFICATION_ID = 1L;

    @LocalServerPort
    private int port;

    public static final String postUrl = "/notification-group";

    @BeforeEach
    public void setUp() throws IOException {
        // Notification group block
        NotificationGroup notificationGroup = new NotificationGroup();
        notificationGroup.setId(1L);
        notificationGroup.setEnabled(true);
        notificationGroup.setName("Notification group name");


        Mockito.when(this.mockNotificationGroup.findById(DEFAULT_NOTIFICATION_ID))
                .thenReturn(Optional.of(notificationGroup));
    }




        @Test
    public void should_create_notification_group_success() {

            NotificationGroupRequest notificationRequest = new NotificationGroupRequest("name",true);
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


    //Put mapping
    @Test
    public void update_notification_group_not_suscess() {

    }

    @Test
    public void update_notification_group_not_found() {

    }

    @Test
    public void update_notification_group_validation_error() {

    }

    //Delete mapping
    @Test
    public void delete_notification_group_success() {

    }

    @Test
    public void delete_notification_group_not_found() {

    }
}