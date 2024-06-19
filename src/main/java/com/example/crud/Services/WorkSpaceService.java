package com.example.crud.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.Request.WorkSpaceDTO;
import com.example.crud.DTO.Response.WorkSpaceDTOResponse;
import com.example.crud.Entity.User;
import com.example.crud.Entity.WorkSpace;
import com.example.crud.Repository.WorkSpaceRepository;

@Service
public class WorkSpaceService {
    @Autowired
    private WorkSpaceRepository workSpaceRepository;
    @Autowired
    private UserService userService;

    public WorkSpaceDTOResponse toWorkSpaceDTOResponse(WorkSpace workSpace) {
        return WorkSpaceDTOResponse.builder()
                .id(workSpace.getId())
                .name(workSpace.getName())
                .createAt(workSpace.getCreateAt())
                .description(workSpace.getDescription())
                .createBy(userService.toUserDTOResponse(workSpace.getCreateBy()))
                .build();

    }

    public List<WorkSpaceDTOResponse> getAllWorkSpace() {
        List<WorkSpace> workSpaces = workSpaceRepository.findAll();
        List<WorkSpaceDTOResponse> workSpaceDTOResponses = new ArrayList<>();
        for (WorkSpace workSpace : workSpaces) {
            workSpaceDTOResponses.add(toWorkSpaceDTOResponse(workSpace));

        }
        return workSpaceDTOResponses;
    }

    public void createWorkSpace(WorkSpaceDTO request, User user) {
        WorkSpace workSpace = WorkSpace.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createAt(LocalDate.now())
                .createBy(user)
                .build();
        workSpaceRepository.save(workSpace);

    }

    public void updateWorkSpace(WorkSpaceDTO request, WorkSpace workSpace) {
        workSpace.setName(request.getName());
        workSpace.setDescription(request.getDescription());
        workSpace.setCreateAt(LocalDate.now());
        workSpaceRepository.save(workSpace);

    }

    public List<WorkSpaceDTOResponse> findWorkSpaceByName(String name) {
        List<WorkSpace> workSpaces = workSpaceRepository.findWSPByName(name);
        List<WorkSpaceDTOResponse> workSpaceDTOResponses = new ArrayList<>();
        for (WorkSpace workSpace : workSpaces) {
            workSpaceDTOResponses.add(toWorkSpaceDTOResponse(workSpace));

        }
        return workSpaceDTOResponses;
    }

}
