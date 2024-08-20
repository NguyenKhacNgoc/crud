package com.example.crud.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.crud.entity.WorkSpace;

@Repository
public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Long> {
    @Query("SELECT w FROM WorkSpace w WHERE w.name = :name")
    Page<WorkSpace> findWSPByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT w FROM WorkSpace w WHERE w.id = :id")
    Optional<WorkSpace> findWSPById(@Param("id") String id);

    @SuppressWarnings("null")
    @Override
    Page<WorkSpace> findAll(Pageable pageable);

}
