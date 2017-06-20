package io.arukas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 20/06/2017.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/hello")
    public String hello(){
        return "content";
    }

}
