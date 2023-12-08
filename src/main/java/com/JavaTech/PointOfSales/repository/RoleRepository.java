package com.JavaTech.PointOfSales.repository;

import com.JavaTech.PointOfSales.model.ERole;
import com.JavaTech.PointOfSales.model.Role;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
















@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {    Optional<Role> findByName(ERole name);

}
