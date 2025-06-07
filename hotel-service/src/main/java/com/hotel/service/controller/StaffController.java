package com.hotel.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @GetMapping
    public ResponseEntity<List<String>> getStaffs() {
        // This is a placeholder for the actual staff data retrieval logic.
        List<String> staffs = List.of("Jacob", "Smith", "David");

        return ResponseEntity.ok(staffs);
    }

}
