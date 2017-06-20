package io.arukas.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 20/06/2017.
 */
@Controller
@RequestMapping("/error")
public class ErrorPageController implements ErrorController {


    @GetMapping(value = "/400")
    public String errorHtml400(HttpServletRequest request, HttpServletResponse response) {
        return "error/400";
    }

    @GetMapping(value = "/404")
    public String errorHtml404(HttpServletRequest request, HttpServletResponse response) {
        return "error/404";
    }

    @GetMapping(value = "/401")
    public String errorHtml401(HttpServletRequest request, HttpServletResponse response) {

        HttpStatus httpStatus = getHttpStatus(request);
        System.out.println(httpStatus.value());
        return "error/401";
    }


    @GetMapping(value = "/500")
    public String errorHtml500(HttpServletRequest request, HttpServletResponse response) {
        return "error/500";
    }

    private HttpStatus getHttpStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
