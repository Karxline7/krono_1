package com.datacenter.mallaturnos.Presentation.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "API Malla Turnos funcionando y operativa xd";
    }
}
