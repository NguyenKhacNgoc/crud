package com.example.crud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.crud.dto.response.WorkSpaceResponse;
import com.example.crud.entity.WorkSpace;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = UserMapper.class)
public interface WorkSpaceMapper {
    WorkSpaceResponse toWorkSpaceResponse(WorkSpace workSpace);
}
