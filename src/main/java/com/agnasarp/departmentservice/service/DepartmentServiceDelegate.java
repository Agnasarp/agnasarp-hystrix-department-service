package com.agnasarp.departmentservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class DepartmentServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @HystrixCommand(fallbackMethod = "callEmployeeServiceAndGetDataFallback")
    public String callEmployeeServiceAndGetData(String department) {

        System.out.println("Getting Department details for " + department);

        String response = restTemplate
                .exchange("http://localhost:8280/getEmployeeDetailsForDepartment/{department}"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<String>() {
                        }, department).getBody();

        System.out.println("Response Received as " + response + " -  " + new Date());

        return "NORMAL FLOW !!! - Department Name -  " + department + " :::  " +
                " Employee Details " + response + " -  " + new Date();
    }

    @SuppressWarnings("unused")
    private String callEmployeeServiceAndGetDataFallback(String department) {

        System.out.println("Employee Service is down!!! fallback route enabled...");

        return "CIRCUIT BREAKER ENABLED!!! No Response From Employee Service at this moment. " +
                " Service will be back shortly - " + new Date();
    }
}