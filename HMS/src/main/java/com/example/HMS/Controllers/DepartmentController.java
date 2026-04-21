package com.example.HMS.Controllers;

import com.example.HMS.dto.DepartmentDto;
import com.example.HMS.repository.DepartmentRepository;
import com.example.HMS.service.DepartmentService;
import com.example.HMS.entity.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto dto)
    {
        DepartmentDto department1 = departmentService.createDepartment(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(department1);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDto>> fetchAllDepartment(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size)
    {
        Page<DepartmentDto> departmentDto = departmentService.fetchAllDepartment(page, size);

        return ResponseEntity.status(HttpStatus.FOUND).body(departmentDto);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDto> fetchDepartmentById(@PathVariable Long departmentId)
    {
        DepartmentDto dto = departmentService.fetchDepartmentById(departmentId);

        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @PutMapping("/update/{departmentId}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long departmentId , @RequestBody DepartmentDto dto)
    {
        DepartmentDto departmentDto = departmentService.updateDepartment(departmentId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(departmentDto);
    }

    @DeleteMapping("/delete/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long departmentId)
    {

        String deptName = departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department Deleted Successfully with id : { "+departmentId +" } and name { "+deptName+" }");
    }

    @GetMapping("/name/{departmentName}")
    public ResponseEntity<DepartmentDto> fetchDeptByDeptName(@PathVariable String departmentName)
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(departmentService.fetchDeptByDeptName(departmentName));
    }

}
