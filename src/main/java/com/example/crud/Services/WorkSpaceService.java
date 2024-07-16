package com.example.crud.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.Request.WorkSpaceCreationRequest;
import com.example.crud.DTO.Request.WorkSpaceUpdateRequest;
import com.example.crud.DTO.Response.WorkSpaceDTOResponse;
import com.example.crud.Entity.WorkSpace;
import com.example.crud.Exception.AppException;
import com.example.crud.Exception.ErrorCode;
import com.example.crud.Repository.UserRepository;
import com.example.crud.Repository.WorkSpaceRepository;

@Service
public class WorkSpaceService {
    @Autowired
    private WorkSpaceRepository workSpaceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public WorkSpaceDTOResponse toWorkSpaceDTOResponse(WorkSpace workSpace) {
        return WorkSpaceDTOResponse.builder()
                .id(workSpace.getId())
                .name(workSpace.getName())
                .createAt(workSpace.getCreateAt())
                .description(workSpace.getDescription())
                .createBy(userService.toUserDTOResponse(workSpace.getCreateBy()))
                .build();

    }

    public WorkSpaceDTOResponse createWorkSpace(WorkSpaceCreationRequest request) {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        WorkSpace workSpace = WorkSpace.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createAt(LocalDate.now())
                .createBy(userRepository.findUserByUserName(userName)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)))
                .build();
        return toWorkSpaceDTOResponse(workSpaceRepository.save(workSpace));

    }

    public WorkSpaceDTOResponse updateWorkSpace(WorkSpaceUpdateRequest request) {
        WorkSpace workSpace = workSpaceRepository.findWSPById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.WSP_NOT_EXISTED));
        workSpace.setCreateAt(LocalDate.now());
        workSpace.setDescription(request.getDescription());
        workSpace.setName(request.getName());
        return toWorkSpaceDTOResponse(workSpaceRepository.save(workSpace));

    }

    public List<WorkSpaceDTOResponse> findWorkSpaceByName(String name) {
        List<WorkSpace> workSpaces = workSpaceRepository.findWSPByName(name);
        if (workSpaces.isEmpty()) {
            throw new AppException(ErrorCode.WSP_NOT_EXISTED);
        }
        List<WorkSpaceDTOResponse> workSpaceDTOResponses = new ArrayList<>();
        for (WorkSpace workSpace : workSpaces) {
            workSpaceDTOResponses.add(toWorkSpaceDTOResponse(workSpace));

        }
        return workSpaceDTOResponses;
    }

    public List<WorkSpaceDTOResponse> findAll(Integer pageNumber, Integer pageSize) {
        Pageable wspPage = PageRequest.of(pageNumber, pageSize);
        List<WorkSpace> workSpaces = workSpaceRepository.findAll(wspPage).getContent();
        List<WorkSpaceDTOResponse> workSpaceDTOResponses = new ArrayList<>();
        for (WorkSpace workSpace : workSpaces) {
            workSpaceDTOResponses.add(toWorkSpaceDTOResponse(workSpace));

        }
        return workSpaceDTOResponses;

    }

}
