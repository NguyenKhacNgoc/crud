package com.example.crud.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.crud.Entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    Optional<User> findUserByUserName(@Param("userName") String userName);

    @SuppressWarnings("null")
    @Override
    Page<User> findAll(Pageable pageable);

}
