package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class apiconsumo {

    @GetMapping("/loggin")
    public String login() {
        return "loggin"; // Esto debería redirigir a login.html
    }
}
