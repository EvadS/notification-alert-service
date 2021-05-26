package com.se.service.notification.dao.repository;

import com.se.service.notification.dao.entity.TemplateVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Evgeniy Skiba
 */

@Repository
public interface TemplateVariablesRepository extends JpaRepository<TemplateVariable, Long> {
    boolean existsByValue(String name);

    Optional<TemplateVariable> findByValue(String value);
}
