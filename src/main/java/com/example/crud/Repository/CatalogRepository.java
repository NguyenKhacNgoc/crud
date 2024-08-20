package com.example.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.entity.Catalog;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, String> {

}
