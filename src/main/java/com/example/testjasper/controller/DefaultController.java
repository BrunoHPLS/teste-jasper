package com.example.testjasper.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testjasper.model.dto.DefaultDto;
import com.example.testjasper.service.DefaultService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/controller")
@RequiredArgsConstructor
public class DefaultController {
    
    private final DefaultService service;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<DefaultDto>> get(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response){
        service.export(response);
    }
}
