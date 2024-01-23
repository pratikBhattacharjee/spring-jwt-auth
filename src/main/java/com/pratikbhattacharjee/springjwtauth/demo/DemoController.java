package com.pratikbhattacharjee.springjwtauth.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class DemoController {
    
    @GetMapping("/api/v1/demo-controller")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from a secured endpoint!");
    }
}
