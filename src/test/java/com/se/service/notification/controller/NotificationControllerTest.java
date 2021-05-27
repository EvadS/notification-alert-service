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

/**
 * Created by Evgeniy Skiba
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = NotificationServiceApplication.class)
class NotificationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationGroupRepository notificationGroupRepository;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("get notification 200")
    public void get_notification_success() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("get notification 404")
    public void get_notification_not_found() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("create notification 200")
    public void create_notification_success() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("create notification 404")
    public void create_notification_not_found() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("create notification 409")
    public void create_notification_incorrect_model() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("create notification 422")
    public void create_notification_validation_error() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("update notification 200")
    public void update_notification_success() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("update notification 409")
    public void update_notification_incorrect_model() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("update notification 422")
    public void update_notification_validation_error() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("update notification 404")
    public void update_notification_not_found() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("change notification status 404")
    public void change_notification_status_not_found() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("change notification status 200")
    public void change_notification_status_success() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("delete notification status 404")
    public void delete_notification_not_fount() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("delete notification status 200")
    public void delete_notification_success() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("Set alert type 200")
    public void set_alert_type_to_notification_success() {
        Assert.assertTrue(false);
    }

    @Test
    @DisplayName("Set alert type 404")
    public void set_alert_type_to_notification_not_Found() {
        Assert.assertTrue(false);
    }

}