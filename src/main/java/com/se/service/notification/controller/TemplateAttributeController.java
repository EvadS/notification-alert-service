package com.se.service.notification.controller;

import com.se.service.notification.model.request.TemplateAttributeListRequest;
import com.se.service.notification.model.request.TemplateAttributeRequest;
import com.se.service.notification.model.response.TemplateAttributeResponse;
import com.se.service.notification.service.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
@RestController
@RequestMapping("/template-attribute")
@Api(value = "Template attributes api")
public class TemplateAttributeController {

    private final TemplateService templateService;

    public TemplateAttributeController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Template attribute details",
            notes = "Get template attribute details by unique identifier", tags = {})
    ResponseEntity getTemplateAttribute(@PathVariable(value = "id") @NotNull Long id) {
        TemplateAttributeResponse templateAttributeResponse = templateService.getTemplateAttribute(id);
        return ResponseEntity.ok(templateAttributeResponse);
    }


    @PostMapping("/create-by-list")
    @ApiOperation(value = "Create attribute.", notes = "Create template attribute from list with attributes")
    ResponseEntity<List<TemplateAttributeResponse>> createAttributeTemplateByList(
            @Valid @RequestBody TemplateAttributeListRequest templateAttributeRequest) {
        List<TemplateAttributeResponse> templateAttributeResponse = templateService.createTemplateAttributeByList(templateAttributeRequest);
        return ResponseEntity.ok(templateAttributeResponse);
    }

    @PostMapping
    @ApiOperation(value = "Create attribute.", notes = "Create template attribute.")
    ResponseEntity<TemplateAttributeResponse> createAttributeTemplate(
            @Valid @RequestBody TemplateAttributeRequest templateAttributeRequest) {
        TemplateAttributeResponse templateAttributeResponse = templateService.createTemplateAttribute(templateAttributeRequest);
        return ResponseEntity.ok(templateAttributeResponse);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update attribute.",
            notes = "Update attribute by request model and uniques template attribute id.")
    ResponseEntity<TemplateAttributeResponse> updateTemplateAttribute(
            @PathVariable(value = "id") @NotNull Long id,
            @RequestBody @Valid TemplateAttributeRequest templateAttributeRequest) {
        TemplateAttributeResponse templateAttributeResponse = templateService.updateTemplateAttribute(id, templateAttributeRequest);
        return ResponseEntity.ok(templateAttributeResponse);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete attribute.", nickname = "delete",
            notes = "Delete template attribute")
    ResponseEntity deleteTemplateAttribute(
            @PathVariable(value = "id") @NotNull Long id) {
        templateService.deleteTemplateAttribute(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/list", produces = "application/json")
    @ApiOperation(value = "Template attributes", nickname = "list",
            notes = "The current attributes list")
    ResponseEntity<List<TemplateAttributeResponse>> pagedTemplateList() {
        List<TemplateAttributeResponse> templateAttributeList = templateService.availableAttributeList();
        return ResponseEntity.ok(templateAttributeList);
    }
}
