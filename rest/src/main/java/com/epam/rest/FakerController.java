package com.epam.rest;

import com.epam.service_api.FakerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakerController {

    private final FakerService fakerService;

    public FakerController(FakerService fakerService) {
        this.fakerService = fakerService;
    }

    @GetMapping("/gen")
    public String generate(@RequestParam("amount") Integer amount) {
        fakerService.generate(amount);
        return "generated " + amount;
    }
}
