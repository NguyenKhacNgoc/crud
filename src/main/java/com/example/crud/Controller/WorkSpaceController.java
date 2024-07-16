package com.example.crud.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.crud.DTO.Request.WorkSpaceCreationRequest;
import com.example.crud.DTO.Request.WorkSpaceUpdateRequest;
import com.example.crud.DTO.Request.idRequest;
import com.example.crud.DTO.Response.ApiResponse;
import com.example.crud.DTO.Response.WorkSpaceDTOResponse;
import com.example.crud.Entity.WorkSpace;
import com.example.crud.Repository.WorkSpaceRepository;
import com.example.crud.Services.WorkSpaceService;

@RestController
@RequestMapping("api/wsp")
@SuppressWarnings({ "unchecked", "rawtypes" })

public class WorkSpaceController {
    @Autowired
    private WorkSpaceService workSpaceService;
    @Autowired
    private WorkSpaceRepository workSpaceRepository;

    @PostMapping("create")
    public ApiResponse<WorkSpaceDTOResponse> createWSP(@RequestBody WorkSpaceCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(workSpaceService.createWorkSpace(request));
        return apiResponse;

    }

    @PutMapping("update")
    public ApiResponse<WorkSpaceDTOResponse> updateWSP(@RequestBody WorkSpaceUpdateRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(workSpaceService.updateWorkSpace(request));
        return apiResponse;

    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteWSP(@RequestBody idRequest request) {
        Optional<WorkSpace> exWSP = workSpaceRepository.findWSPById(request.getId());
        if (exWSP.isPresent()) {
            workSpaceRepository.delete(exWSP.get());
            return ResponseEntity.ok().body("Xoá thành công");
        } else
            return ResponseEntity.badRequest().body("Work Space không tồn tại");

    }

    @GetMapping("findWSP")
    public ApiResponse<WorkSpaceDTOResponse> findWSP(@RequestParam("name") String name) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(workSpaceService.findWorkSpaceByName(name));
        return apiResponse;
    }

    @GetMapping("findAll")
    public ApiResponse<WorkSpaceDTOResponse> findAllWSP(@RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(workSpaceService.findAll(pageNumber, pageSize));
        return apiResponse;

    }

}
