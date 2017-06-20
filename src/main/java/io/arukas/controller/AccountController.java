package io.arukas.controller;

import io.arukas.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Administrator on 20/06/2017.
 */
@Controller
public class AccountController {

    /**
     * 跳转到login页面
     * @return
     */
    @GetMapping(value = "/account/login")
    public String login(){
        return "account/login";
    }

    @PostMapping(value = "/account/login")
    public String login(User user){
        System.out.println(user.getUsername());
        return "redirect:/";
    }

}
