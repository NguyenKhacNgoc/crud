package com.example.crud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.Entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
