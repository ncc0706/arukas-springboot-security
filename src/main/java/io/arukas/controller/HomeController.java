package io.arukas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Administrator on 20/06/2017.
 */
@Controller
public class HomeController {

    @GetMapping(value = {"", "/"})
    public String home(){
        return "home";
    }

}
