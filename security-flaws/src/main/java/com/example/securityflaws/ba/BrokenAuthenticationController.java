package com.example.securityflaws.ba;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/flaws/ba")
@RequiredArgsConstructor
public class BrokenAuthenticationController {

    @RequestMapping(method = {POST, GET, DELETE, PUT})
    public String get() {
        return "Hello";
    }
}
