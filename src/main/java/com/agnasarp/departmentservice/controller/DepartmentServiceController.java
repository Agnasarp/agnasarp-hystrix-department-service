package com.agnasarp.departmentservice.controller;

import com.agnasarp.departmentservice.service.DepartmentServiceDelegate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentServiceController {

    private DepartmentServiceDelegate departmentServiceDelegate;

    public DepartmentServiceController(DepartmentServiceDelegate departmentServiceDelegate) {
        this.departmentServiceDelegate = departmentServiceDelegate;
    }

    @GetMapping(value = "/getDepartmentDetails/{department}")
    public String getEmployees(@PathVariable String department) {
        System.out.println("Going to call employee service to get data!");
        return departmentServiceDelegate.callEmployeeServiceAndGetData(department);
    }
}