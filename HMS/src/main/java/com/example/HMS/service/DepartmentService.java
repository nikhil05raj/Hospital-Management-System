package com.example.HMS.service;

import com.example.HMS.dto.DepartmentDto;
import com.example.HMS.dto.DepartmentMapper;
import com.example.HMS.entity.Department;
import com.example.HMS.exception.DepartmentNotFoundException;
import com.example.HMS.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    // Create Department
    public DepartmentDto createDepartment(DepartmentDto dto)
    {
        Department dept = departmentMapper.toEntity(dto);
        Department department = departmentRepository.save(dept);

        return departmentMapper.toDto(department);
    }

    // get All Department
    @Cacheable(value = "allDepartments")
    public Page<DepartmentDto> fetchAllDepartment(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);

        Page<Department> departments = departmentRepository.findAll(pageable);
        
        return departments.map(departmentMapper::toDto);
    }

    // get Department by ID
    @Cacheable(value = "departments", key = "#id")
    public DepartmentDto fetchDepartmentById(Long departmentId)
    {
        if (departmentId == null ) {
            throw new IllegalArgumentException("Department Id cannot be empty");
        }

        Optional<Department> OptDepartment = departmentRepository.findById(departmentId);

        if (OptDepartment.isEmpty()){
            throw new DepartmentNotFoundException("Failed to find the department");
        }

        Department department = OptDepartment.get();

        return departmentMapper.toDto(department);
    }

    // Update Department
    @Caching(
            put = {
                    @CachePut(value = "departments", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "allDepartments", allEntries = true),
                    @CacheEvict(value = "departmentByNam",  allEntries = true)
            }
    )
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto dto)
    {
        Optional<Department> dept = departmentRepository.findById(departmentId);

        if (dept.isEmpty()){
            throw new DepartmentNotFoundException(String.format("Failed to find the department with id %d",departmentId));
        }

        Department department = dept.get();

        department.setDepartmentName(dto.getDepartmentName());

        Department savedDepartment = departmentRepository.save(department) ;

        return departmentMapper.toDto(savedDepartment);
    }

    // delete Department
    @Caching(evict = {
            @CacheEvict(value = "departments", key = "#id"),
            @CacheEvict(value = "allDepartments", allEntries = true),
            @CacheEvict(value = "departmentByNam",  allEntries = true)
    })
    public String deleteDepartment(Long departmentId)
    {
        Optional<Department> department = departmentRepository.findById(departmentId);

        String deptName = department.get().getDepartmentName();

        if (department.isPresent()){
            departmentRepository.deleteById(departmentId);
        }
        else {
            throw new DepartmentNotFoundException(String.format("Department doesn't exist with id: %d", departmentId));
        }

        return deptName;
    }

    // get Department by Name
    @Cacheable(value = "departmentByName", key = "#departmentName")
    public DepartmentDto fetchDeptByDeptName(String departName)
    {
        if (departName == null){
            throw new IllegalArgumentException("department name can't be null");
        }
        Department department = departmentRepository.findByDepartmentName(departName);

        if (department == null) {
            throw new DepartmentNotFoundException("Department not found with name: " + departName);
        }

        return departmentMapper.toDto(department);
    }


}