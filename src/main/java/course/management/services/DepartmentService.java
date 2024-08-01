package course.management.services;

import course.management.DTOs.departmentDTOs.DepartmentCreateDTO;
import course.management.DTOs.departmentDTOs.DepartmentDTO;
import course.management.DTOs.departmentDTOs.DepartmentUpdateDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentCreateDTO departmentDTO);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO updateDepartment(Long id, DepartmentUpdateDTO departmentDTO);

    void deleteDepartment(Long id);

    Float getAvgById(Long id);
}
