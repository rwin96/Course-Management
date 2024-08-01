package course.management.controllers.api;

import course.management.DTOs.departmentDTOs.DepartmentCreateDTO;
import course.management.DTOs.departmentDTOs.DepartmentDTO;
import course.management.DTOs.departmentDTOs.DepartmentUpdateDTO;
import course.management.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    // Create
    @PostMapping(name = "CreateDepartment")
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentCreateDTO departmentCreateDTO) {
        DepartmentDTO departmentDTO = departmentService.createDepartment(departmentCreateDTO);
        return new ResponseEntity<>(departmentDTO, HttpStatus.CREATED);
    }

    // Read
//    @PreAuthorize("hasRole('ADMIN') or (hasRole('HEAD') and #id == ((T(course.management.helper.CustomUserDetailsImpl)) authentication.principal).getDepartmentId())")
    @GetMapping(name = "GetDepartment", value = "/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }

    @GetMapping(name = "GetAllDepartments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departmentDTOS = departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    // Update
    @PutMapping(name = "UpdateDepartment", value = "/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentUpdateDTO departmentDTO) {
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping(name = "DeleteDepartment", value = "/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(("Department with ID '" + id + "' has been deleted successfully."), HttpStatus.OK);
    }

    @GetMapping(name = "GetAllStudentsAverages", value = "/{id}/avg")
    public ResponseEntity<Float> getAllStudentsAverages(@PathVariable Long id) {
        Float avg = departmentService.getAvgById(id);
        return new ResponseEntity<>(avg, HttpStatus.OK);
    }
}
