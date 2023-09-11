package com.crud.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
