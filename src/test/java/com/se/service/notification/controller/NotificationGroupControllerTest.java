package com.se.service.notification.controller;

import com.se.service.notification.NotificationServiceApplication;
import com.se.service.notification.dao.entity.NotificationGroup;
import com.se.service.notification.dao.repository.NotificationGroupRepository;
import com.se.service.notification.model.request.NotificationGroupRequest;
import com.se.service.notification.model.response.NotificationGroupResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public static final String getByIdUrl = "/notification-group";
    public static final String deleteUrl = "/notification-group";
    public static final String changeStatusUrl = "/status/{id}/{status}";

    public static final String DEFAULT_BASE_NAME = "name";
    public static final boolean DEFAULT_GROUP_ENABLED = true;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationGroupRepository notificationGroupRepository;

    @LocalServerPort
    private int port;

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {

        NotificationGroup notificationGroup = new NotificationGroup("test", true);
        NotificationGroup genericEntity = notificationGroupRepository.save(notificationGroup);

        Long generatedId = genericEntity.getId();
        NotificationGroup foundEntity = notificationGroupRepository.getOne(generatedId);

        assertNotNull(foundEntity);
        org.junit.jupiter.api.Assertions.assertEquals(genericEntity.getName(), foundEntity.getName());
    }

    @DisplayName("create notification group 200")
    @Test
    public void should_create_notification_group_success() {

        NotificationGroupRequest notificationRequest =
                new NotificationGroupRequest(DEFAULT_BASE_NAME, DEFAULT_GROUP_ENABLED);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<NotificationGroupResponse> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                notificationRequest,
                NotificationGroupResponse.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getName(), DEFAULT_BASE_NAME);
        assertEquals(response.getBody().isStatus(), DEFAULT_GROUP_ENABLED);
    }

    @DisplayName("create notification group 422")
    @Test
    public void should_create_notification_group_validation_error() {

        String incorrectNotificationRequestType = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<NotificationGroupResponse> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                incorrectNotificationRequestType,
                NotificationGroupResponse.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
        assertNotNull(response.getBody());

    }

    @DisplayName("create notification group 409")
    @Test
    public void create_notification_group_already_exists() {

        notificationGroupRepository.deleteAllInBatch();

        NotificationGroup notificationGroup = new NotificationGroup(DEFAULT_BASE_NAME, true);
        notificationGroupRepository.save(notificationGroup);

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest();
        notificationRequest.setName(DEFAULT_BASE_NAME);
        notificationRequest.setEnabled(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/" + postUrl,
                notificationRequest,
                Object.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
        assertNotNull(response.getBody());

        notificationGroupRepository.deleteAllInBatch();
    }

    @DisplayName("delete notification group correct")
    @Test
    public void should_delete_correct() {

        notificationGroupRepository.deleteAllInBatch();

        NotificationGroup notificationGroup = new NotificationGroup(DEFAULT_BASE_NAME, true);
        NotificationGroup notificationGroupGenerated = notificationGroupRepository.save(notificationGroup);

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest();
        notificationRequest.setName(DEFAULT_BASE_NAME);
        notificationRequest.setEnabled(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(notificationGroupGenerated.getId()));
        restTemplate.delete(
                "http://localhost:" + port + "/" + deleteUrl + "/" + notificationGroupGenerated.getId(),
                HttpMethod.DELETE, params);

        assertThat(notificationGroupRepository.findAll().size(), is(0));
    }

    @DisplayName("delete notification group 404")
    @Test
    public void should_delete_not_found() {

        notificationGroupRepository.deleteAllInBatch();

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest();
        notificationRequest.setName(DEFAULT_BASE_NAME);
        notificationRequest.setEnabled(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/" + deleteUrl + "/" + 1000;
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE, null, ResponseEntity.class);


        assertThat(notificationGroupRepository.findAll().size(), is(1));
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));

    }


    @DisplayName("update notification group 404")
    @Test
    public void update_notification_group_success() {
        notificationGroupRepository.deleteAllInBatch();

        NotificationGroup notificationGroup = new NotificationGroup(DEFAULT_BASE_NAME, DEFAULT_GROUP_ENABLED);
        NotificationGroup genericEntity = notificationGroupRepository.save(notificationGroup);

        NotificationGroupRequest notificationRequest = new NotificationGroupRequest("test2", true);

        ResponseEntity<NotificationGroupResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/" + putUrl + "/" + genericEntity.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(notificationRequest),
                NotificationGroupResponse.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        assertNotNull(response.getBody());
        assertEquals(response.getBody().getName(), DEFAULT_BASE_NAME);
        assertEquals(response.getBody().isStatus(), DEFAULT_GROUP_ENABLED);

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


 
}