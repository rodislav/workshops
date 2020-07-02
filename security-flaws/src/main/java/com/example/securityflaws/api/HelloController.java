package com.example.securityflaws.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {

    @RequestMapping(method = {POST, GET, DELETE, PUT})
    public String get() {
        return "Hello";
    }
}
