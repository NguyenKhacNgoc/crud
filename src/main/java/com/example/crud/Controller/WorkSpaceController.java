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

import com.example.crud.DTO.Request.WorkSpaceDTO;
import com.example.crud.Entity.User;
import com.example.crud.Entity.WorkSpace;
import com.example.crud.Repository.UserRepository;
import com.example.crud.Repository.WorkSpaceRepository;
import com.example.crud.Services.WorkSpaceService;

@RestController
@RequestMapping("/api")
// @Api(value = "User Management System", description = "Operations pertaining
// to user management")

public class WorkSpaceController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkSpaceService workSpaceService;
    @Autowired
    private WorkSpaceRepository workSpaceRepository;

    // @ApiOperation(value = "View a list of available users", response =
    // List.class)
    // @ApiResponses(value = {
    // @ApiResponse(code = 200, message = "Successfully retrieved list"),
    // @ApiResponse(code = 401, message = "You are not authorized to view the
    // resource"),
    // @ApiResponse(code = 403, message = "Accessing the resource you were trying to
    // reach is forbidden"),
    // @ApiResponse(code = 404, message = "The resource you were trying to reach is
    // not found")
    // })

    @GetMapping("getAllWSP")
    public ResponseEntity<?> getAllWSP() {
        if (workSpaceService.getAllWorkSpace().isEmpty()) {
            return ResponseEntity.status(404).body("Rỗng");
        }
        return ResponseEntity.ok(workSpaceService.getAllWorkSpace());
    }

    @PostMapping("createWSP")
    public ResponseEntity<?> createWSP(@RequestBody WorkSpaceDTO request) {
        Optional<User> exUser = userRepository.findById(request.getCreateBy());
        if (exUser.isPresent()) {
            workSpaceService.createWorkSpace(request, exUser.get());
            return ResponseEntity.ok().body("Tạo Work Space thành công");
        } else
            return ResponseEntity.badRequest().body("Không tìm thấy người dùng");

    }

    @PutMapping("updateWSP")
    public ResponseEntity<?> updateWSP(@RequestBody WorkSpaceDTO request) {
        Optional<WorkSpace> exWSP = workSpaceRepository.findById(request.getId());
        if (exWSP.isPresent()) {
            workSpaceService.updateWorkSpace(request, exWSP.get());
            return ResponseEntity.ok().body("Sửa thành công");
        } else
            return ResponseEntity.badRequest().body("Work Space này không tồn tại");

    }

    @DeleteMapping("deleteWSP")
    public ResponseEntity<?> deleteWSP(@RequestBody WorkSpaceDTO request) {
        Optional<WorkSpace> exWSP = workSpaceRepository.findById(request.getId());
        if (exWSP.isPresent()) {
            workSpaceRepository.delete(exWSP.get());
            return ResponseEntity.ok().body("Xoá thành công");
        } else
            return ResponseEntity.badRequest().body("Work Space không tồn tại");

    }

    @GetMapping("findWSP")
    public ResponseEntity<?> findWSP(@RequestParam("name") String name) {
        if (workSpaceService.findWorkSpaceByName(name).isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy WSP");
        }
        return ResponseEntity.ok(workSpaceService.findWorkSpaceByName(name));
    }

}
