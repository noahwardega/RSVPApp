package com.planit.enterprise.repository;

import com.planit.enterprise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findBylName(String lName); // Updated to match entity field name
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);


}
