package com.example.crud.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crud.dto.request.CatalogRequest;
import com.example.crud.dto.response.CatalogResponse;
import com.example.crud.entity.Catalog;
import com.example.crud.mapper.CatalogMapper;
import com.example.crud.repository.CatalogRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CatalogService {
    CatalogRepository catalogRepository;
    CatalogMapper catalogMapper;

    public CatalogResponse createCatalog(CatalogRequest request) {
        return catalogMapper.toCatalogResponse(catalogRepository.save(catalogMapper.toCatalog(request)));
    }

    List<CatalogResponse> addCatalogResponse(List<Catalog> catalogs) {
        List<CatalogResponse> catalogResponses = new ArrayList<>();
        for (Catalog catalog : catalogs) {
            catalogResponses.add(catalogMapper.toCatalogResponse(catalog));
        }
        return catalogResponses;

    }

    public List<CatalogResponse> getAll() {
        return addCatalogResponse(catalogRepository.findAll());

    }

    public String deleteCatalog(String catalog) {
        catalogRepository.deleteById(catalog);
        return "success";
    }

}
