package com.example.crud.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.dto.request.CatalogRequest;
import com.example.crud.dto.response.ApiResponse;
import com.example.crud.services.CatalogService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/catalog")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CatalogController {
    CatalogService catalogService;

    @PostMapping("create")
    public ApiResponse<?> createCatalog(@RequestBody CatalogRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(catalogService.createCatalog(request));
        return apiResponse;
    }

    @GetMapping("catalogs")
    public ApiResponse<?> getAllCatalog() {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(catalogService.getAll());
        return apiResponse;
    }

    @DeleteMapping("{catalog}")
    public ApiResponse<?> deleteCatalog(@PathVariable("catalog") String catalog) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(catalogService.deleteCatalog(catalog));
        return apiResponse;
    }

}
