package com.qa.userapp.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.userapp.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
