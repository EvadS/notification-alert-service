package com.se.service.notification.dao.entity;

import com.se.service.notification.dao.entity.common.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Created by Evgeniy Skiba
 */
@Entity(name = "TemplateVariable")
@Table(name = "template_variable")
public class TemplateVariable extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Value cannot be null or empty ")
    @Column(name = "value", columnDefinition = "text")
    private String value;

    public TemplateVariable() {
    }

    public TemplateVariable(@NotBlank(message = "Value cannot be null or empty ") String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String name) {
        this.value = name;
    }
}
