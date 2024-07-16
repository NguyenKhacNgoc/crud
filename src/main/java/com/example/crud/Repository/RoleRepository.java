package com.example.crud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
