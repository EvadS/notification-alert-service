package com.se.service.notification.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Evgeniy Skiba on 13.05.21
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserComponentException extends RuntimeException {

    private final String host;
    private final int port;
    private final String api;

    public UserComponentException(String host, int port, String api) {
        super(String.format("Error while send information to communication service %s:%s, api: '%s'.",
                host,port,api));

        this.host = host;
        this.port = port;
        this.api = api;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getApi() {
        return api;
    }
}
