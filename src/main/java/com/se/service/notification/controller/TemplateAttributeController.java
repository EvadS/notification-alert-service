package com.se.service.notification.controller;

import com.se.service.notification.controller.base.TemplateAttributeControllerBase;
import com.se.service.notification.handler.model.ErrorResponse;
import com.se.service.notification.model.request.TemplateAttributeListRequest;
import com.se.service.notification.model.request.TemplateAttributeRequest;
import com.se.service.notification.model.response.NotificationGroupResponse;
import com.se.service.notification.model.response.TemplateAttributeResponse;
import com.se.service.notification.model.response.TemplateAttributeResponseList;
import com.se.service.notification.service.TemplateService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Evgeniy Skiba
 */
@RestController
@RequestMapping("/template-attribute")
@Api(value = "Template attributes management api")
public class TemplateAttributeController implements TemplateAttributeControllerBase {

    private final TemplateService templateService;

    public TemplateAttributeController(TemplateService templateService) {
        this.templateService = templateService;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved template attribute",
                    response = TemplateAttributeResponse.class),
            @ApiResponse(code = 404, message = "template attribute was not found by unique identifier",
                    response = ErrorResponse.class)
    })
    @GetMapping("/{id}")
    @ApiOperation(value = "Template attribute details",
            notes = "Get template attribute details by unique identifier")
    ResponseEntity<TemplateAttributeResponse> getTemplateAttribute(
            @ApiParam(value = "Place holder unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id) {
        TemplateAttributeResponse templateAttributeResponse = templateService.getTemplateAttribute(id);
        return ResponseEntity.ok(templateAttributeResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created list of template attributes",
                    response = TemplateAttributeResponseList.class),
            @ApiResponse(code = 400, message = "Incorrect template attributes name to create", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Incorrect model to create template attributes", response = ErrorResponse.class),
    })
    @PostMapping("/create-by-list")
    @ApiOperation(value = "Create template place holders",
            notes = "Create place holders from list with attributes")
    ResponseEntity<TemplateAttributeResponseList> createAttributeTemplateByList(
            @Valid @RequestBody TemplateAttributeListRequest templateAttributeRequest) {
        TemplateAttributeResponseList templateAttributeResponse = templateService.createTemplateAttributeByList(templateAttributeRequest);
        return ResponseEntity.ok(templateAttributeResponse);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created template attribute model",
                    response = TemplateAttributeResponse.class),
            @ApiResponse(code = 409, message = "Incorrect template attribute name to create", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Incorrect model to create template attribute", response = ErrorResponse.class),
    })
      @ApiOperation(value = "Create one attribute.", notes = "Create one template placeholder.")
    ResponseEntity<TemplateAttributeResponse> createAttributeTemplate(
            @Valid @RequestBody TemplateAttributeRequest templateAttributeRequest) {
        TemplateAttributeResponse templateAttributeResponse = templateService.createTemplateAttribute(templateAttributeRequest);
        return ResponseEntity.ok(templateAttributeResponse);
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated attribute",
                    response = TemplateAttributeResponse.class),
            @ApiResponse(code = 400, message = "Incorrect Update attribute name to create", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Incorrect model to update attribute", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Attribute doesn't exists by unique identifier", response = ErrorResponse.class)
    })
    @PutMapping("/{id}")
    @ApiOperation(value = "Update attribute.",
            notes = "Update attribute by request model and uniques template attribute id.")
    ResponseEntity<TemplateAttributeResponse> updateTemplateAttribute(
            @ApiParam(value = "unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id,
            @RequestBody @Valid TemplateAttributeRequest templateAttributeRequest) {
        TemplateAttributeResponse templateAttributeResponse = templateService.updateTemplateAttribute(id, templateAttributeRequest);
        return ResponseEntity.ok(templateAttributeResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "attribute success deleted",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 404, message = "attribute doesn't exists by unique identifier", response = ErrorResponse.class)
    })
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete attribute.", nickname = "delete",
            notes = "Delete template attribute")
    ResponseEntity deleteTemplateAttribute(
            @ApiParam(value = "unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id) {
        templateService.deleteTemplateAttribute(id);
        return ResponseEntity.accepted().build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Available template attribute list",
                    response = TemplateAttributeResponseList.class),
    })
    @GetMapping(value = "/list", produces = "application/json")
    @ApiOperation(value = "Template attributes", nickname = "list",
            notes = "The current attributes list")
    ResponseEntity<TemplateAttributeResponseList> pagedTemplateList() {
        TemplateAttributeResponseList templateAttributeList = templateService.availableAttributeList();
        return ResponseEntity.ok(templateAttributeList);
    }
}
