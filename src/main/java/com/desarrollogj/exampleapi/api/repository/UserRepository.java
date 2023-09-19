package com.desarrollogj.exampleapi.api.repository;

import com.desarrollogj.exampleapi.api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u " +
            "WHERE (u.firstName LIKE :firstName OR :firstName IS NULL) " +
            "AND (u.lastName LIKE :firstName OR :lastName IS NULL) " +
            "AND (u.email LIKE :firstName OR :email IS NULL) " +
            "AND (u.active = true OR :onlyActives IS NULL)")
    Page<User> findBySearch(String firstName, String lastName, String email, Boolean onlyActives, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.active = true")
    List<User> findAllActive();

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.active = true")
    Optional<User> findActiveById(long id);

    @Query("SELECT u FROM User u WHERE u.reference = :reference AND u.active = true")
    Optional<User> findActiveByReference(String reference);
}
