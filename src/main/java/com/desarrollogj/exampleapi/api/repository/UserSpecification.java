package com.desarrollogj.exampleapi.api.repository;

import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSearchInput;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserSpecification {
    public static Specification<User> findActiveUsers(UserSearchInput input) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("active"), true));

            if (input.getFirstName() != null && !input.getFirstName().isEmpty()) {
                var value = String.format("%s%%", input.getFirstName().toLowerCase(Locale.ROOT));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), value));
            }

            if (input.getLastName() != null && !input.getLastName().isEmpty()) {
                var value = String.format("%s%%", input.getLastName().toLowerCase(Locale.ROOT));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), value));
            }

            if (input.getEmail() != null && !input.getEmail().isEmpty()) {
                var value = String.format("%s%%", input.getEmail().toLowerCase(Locale.ROOT));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), value));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
