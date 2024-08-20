package com.example.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.dto.request.WorkSpaceCreationRequest;
import com.example.crud.dto.request.WorkSpaceUpdateRequest;
import com.example.crud.dto.response.ApiResponse;
import com.example.crud.dto.response.PageResponse;
import com.example.crud.dto.response.WorkSpaceResponse;
import com.example.crud.services.WorkSpaceService;

@RestController
@RequestMapping("api")
@SuppressWarnings({ "unchecked", "rawtypes" })

public class WorkSpaceController {
    @Autowired
    private WorkSpaceService workSpaceService;

    @PostMapping("wsp/create")
    public ApiResponse<WorkSpaceResponse> createWSP(@RequestBody WorkSpaceCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(workSpaceService.createWorkSpace(request));
        return apiResponse;

    }

    @PutMapping("wsp/update/{id}")
    public ApiResponse<WorkSpaceResponse> updateWSP(@RequestBody WorkSpaceUpdateRequest request,
            @PathVariable String id) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(workSpaceService.updateWorkSpace(request, id));
        return apiResponse;

    }

    @DeleteMapping("wsp/delete/{id}")
    public void deleteWSP(@PathVariable String id) {
        workSpaceService.deleteWorkSpace(id);

    }

    @GetMapping("wsp")
    public ApiResponse<PageResponse<WorkSpaceResponse>> findWSP(@RequestParam("name") String name,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(workSpaceService.findWorkSpaceByName(name, page, size));
        return apiResponse;
    }

    @GetMapping("wsps")
    public ApiResponse<PageResponse<WorkSpaceResponse>> findAllWSP(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(workSpaceService.findAll(page, size));
        return apiResponse;

    }

}
