package com.JavaTech.PointOfSales.repository;


import com.JavaTech.PointOfSales.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public Optional<User> getUserByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void addUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    Optional<User> findByUsername(String username);
    User findUserById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmailIgnoreCase(String emailId);
}
