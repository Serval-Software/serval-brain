package com.serval.servalbrain.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class MainController {

    @GetMapping("/message")
    public String getMessage() {
        return "It is working!";
    }
}
