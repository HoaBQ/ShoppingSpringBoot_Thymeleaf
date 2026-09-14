package com.shopping.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findByUsernameContainingIgnoreCase(String keyword, Pageable pageable);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}