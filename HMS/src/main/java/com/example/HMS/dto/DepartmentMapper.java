package com.example.HMS.dto;

import com.example.HMS.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    // Dto ---> Entity
    public Department toEntity(DepartmentDto dto)
    {
        if (dto == null) { return null; }

        Department department = new Department();
        department.setDepartmentName(dto.getDepartmentName());

        return department;
    }

    // Entity ---> Dto
    public DepartmentDto toDto(Department department)
    {
        if (department == null){ return null; }

        DepartmentDto dto =new DepartmentDto();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());

        return dto;
    }
}
