package com.TaskManagement.Task.service;


import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.UserRole;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;



@Service
public class FiltersService {

    public static <T> Specification<T> startsWith(String fieldName, String value) {
        return (root, query, cb) -> cb.like(root.get(fieldName), value + "%");
    }

    public static <T> Specification<T> contains(String fieldName, String value) {
        return (root, query, cb) -> cb.like(root.get(fieldName), "%" + value + "%");
    }

    public static <T> Specification<T> notContains(String fieldName, String value) {
        return (root, query, cb) -> cb.notLike(root.get(fieldName), "%" + value + "%");
    }

    public static <T> Specification<T> endsWith(String fieldName, String value) {
        return (root, query, cb) -> cb.like(root.get(fieldName), "%" + value);
    }

    public static <T> Specification<T> equals(String fieldName, Object value) {
        return (root, query, cb) -> cb.equal(root.get(fieldName), value);
    }

    public static <T> Specification<T> notEquals(String fieldName, Object value) {
        return (root, query, cb) -> cb.notEqual(root.get(fieldName), value);
    }

    public static <T> Specification<T> matchAll(List<Specification<T>> specs) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Specification<T> spec : specs) {
                predicates.add(spec.toPredicate(root, query, cb));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static <T> Specification<T> matchAny(List<Specification<T>> specs) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Specification<T> spec : specs) {
                predicates.add(spec.toPredicate(root, query, cb));
            }
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
/////ce filter est seulement pour la classe UserrEntity
    public Specification<UserEntity> withRoles(List<String> roleNames) {
        return (root, query, criteriaBuilder) -> {
            Join<UserEntity, UserRole> rolesJoin = root.join("roles");
            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(rolesJoin.get("name"));
            for (String roleName : roleNames) {
                inClause.value(roleName);
            }
            return criteriaBuilder.and(inClause);
        };
    }



}



