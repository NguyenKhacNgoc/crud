package com.example.crud.services;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.crud.dto.request.WorkSpaceCreationRequest;
import com.example.crud.dto.request.WorkSpaceUpdateRequest;
import com.example.crud.dto.response.PageResponse;
import com.example.crud.dto.response.WorkSpaceResponse;
import com.example.crud.entity.WorkSpace;
import com.example.crud.exception.AppException;
import com.example.crud.exception.ErrorCode;
import com.example.crud.mapper.WorkSpaceMapper;
import com.example.crud.repository.UserRepository;
import com.example.crud.repository.WorkSpaceRepository;

@Service
public class WorkSpaceService {
    @Autowired
    private WorkSpaceRepository workSpaceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkSpaceMapper workSpaceMapper;

    public WorkSpaceResponse createWorkSpace(WorkSpaceCreationRequest request) {
        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();
        WorkSpace workSpace = WorkSpace.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createAt(LocalDate.now())
                .createBy(userRepository.findById(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)))
                .build();
        return workSpaceMapper.toWorkSpaceResponse(workSpaceRepository.save(workSpace));

    }

    public WorkSpaceResponse updateWorkSpace(WorkSpaceUpdateRequest request, String id) {
        WorkSpace workSpace = workSpaceRepository.findWSPById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WSP_NOT_EXISTED));
        workSpace.setCreateAt(LocalDate.now());
        workSpace.setDescription(request.getDescription());
        workSpace.setName(request.getName());
        return workSpaceMapper.toWorkSpaceResponse(workSpaceRepository.save(workSpace));

    }

    public void deleteWorkSpace(String id) {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();

        WorkSpace workSpace = workSpaceRepository.findWSPById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WSP_NOT_EXISTED));
        if (userId == workSpace.getCreateBy().getId()) {
            workSpaceRepository.delete(workSpace);
        } else
            throw new AppException(ErrorCode.UNAUTHORIZED);

    }

    public PageResponse<WorkSpaceResponse> findWorkSpaceByName(String name, int page, int size) {
        Sort sort = Sort.by("createAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = workSpaceRepository.findWSPByName(name, pageable);
        return PageResponse.<WorkSpaceResponse>builder().currentPage(page).pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages()).totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(workSpaceMapper::toWorkSpaceResponse).toList()).build();
    }

    public PageResponse<WorkSpaceResponse> findAll(int page, int size) {
        Sort sort = Sort.by("createAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = workSpaceRepository.findAll(pageable);
        return PageResponse.<WorkSpaceResponse>builder().currentPage(page).pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages()).totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(workSpaceMapper::toWorkSpaceResponse).toList()).build();

    }

}
