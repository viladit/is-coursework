package com.example.is_curse_work.repository;

import com.example.is_curse_work.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u left join fetch u.roles where u.email = :email")
    Optional<User> findByEmailFetchRoles(@Param("email") String email);

    @EntityGraph(attributePaths = {"roles"})
    @Query("select u from User u order by u.userId")
    java.util.List<User> findAllWithRoles();

    @EntityGraph(attributePaths = {"roles"})
    @Query("select u from User u where u.userId = :id")
    Optional<User> findByIdWithRoles(@Param("id") Long id);
}
