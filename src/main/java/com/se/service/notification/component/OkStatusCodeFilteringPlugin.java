package com.se.service.notification.component;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

import java.util.Arrays;

/**
 * Created by Evgeniy Skiba on 26.05.21
 */
@Component
public class OkStatusCodeFilteringPlugin implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext operationContext) {

        if (!operationContext.httpMethod().equals(HttpMethod.GET)) {
            operationContext
                    .operationBuilder()
                    .build()
                    .getResponseMessages()
                    .removeIf(responseMessage -> Arrays.asList(
                            HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.ACCEPTED.value(),
                            HttpStatus.CREATED.value(),
                            HttpStatus.FORBIDDEN.value())
                            .contains(responseMessage.getCode()));
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}