package com.example.crud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.crud.dto.request.CatalogRequest;
import com.example.crud.dto.response.CatalogResponse;
import com.example.crud.entity.Catalog;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatalogMapper {
    Catalog toCatalog(CatalogRequest request);

    CatalogResponse toCatalogResponse(Catalog catalog);

}
