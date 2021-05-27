package com.se.service.notification.controller;

import com.se.service.notification.NotificationServiceApplication;
import com.se.service.notification.dao.repository.NotificationGroupRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Evgeniy Skiba on 27.05.21
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = NotificationServiceApplication.class)
class TemplateAttributeControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationGroupRepository notificationGroupRepository;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Get template attribute  200")
    public void get_attribute_success() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("Get template attribute  404")
    public void get_attribute_not_found() {

    }

    @Test
    @DisplayName("Create template attribute 200")
    public void create_attribute_success() {

    }

    @Test
    @DisplayName("Create template attribute 409")
    public void create_attribute_conflict() {

    }

    @Test
    @DisplayName("Create template attribute 422")
    public void create_attribute_validation_error() {

    }


    @Test
    @DisplayName("Create template attribute from list 200")
    public void create_attribute_from_list_success() {

    }

    @Test
    @DisplayName("Create template attribute  from list 409")
    public void create_notification_from_list_success() {

    }

    @Test
    @DisplayName("Create template attribute from list 422")
    public void create_notification_from_list_validation_error() {

    }

    @Test
    @DisplayName("update template attribute 200")
    public void update_notification_success() {

    }

    @Test
    @DisplayName("update template attribute 409")
    public void update_notification_409() {

    }

    @Test
    @DisplayName("update template attribute 422")
    public void update_notification_from_list_validation_error() {

    }

    @Test
    @DisplayName("delete template attribute 202")
    public void delete_template_sucess() {

    }

    @Test
    @DisplayName("delete template attribute 404")
    public void delete_template_not_found() {

    }

}

