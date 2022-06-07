package com.demo;

import io.micronaut.http.annotation.*;

@Controller("/first")
public class FirstController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}