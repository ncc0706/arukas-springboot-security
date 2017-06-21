package io.arukas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 20/06/2017.
 */
@Controller
@RequestMapping("/error")
public class ErrorPageController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    public ErrorPageController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @GetMapping(value = "/400")
    public String errorHtml400(HttpServletRequest request, HttpServletResponse response) {
        return "error/400";
    }


    @GetMapping(value = "/401")
    public String errorHtml401(HttpServletRequest request, HttpServletResponse response) {

        HttpStatus httpStatus = getHttpStatus(request);
        System.out.println(httpStatus.value());
        return "error/401";
    }

    @GetMapping(value = "/403")
    public String errorHtml403(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus httpStatus = getHttpStatus(request);
        System.out.println(httpStatus.value());
        return "error/403";
    }

    @GetMapping(value = "/404")
    public String errorHtml404(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(getHttpStatus(request).value());
        Map<String, Object> map = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        request.setAttribute("map", map);
        return "error/404";
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

    protected boolean isIncludeStackTrace(HttpServletRequest request,
                                          MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    /**
     * 获取错误信息
     * @param request
     * @param includeStackTrace
     * @return
     */
    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return this.errorAttributes.getErrorAttributes(requestAttributes,
                includeStackTrace);
    }

    /**
     * 是否包含trace
     * @param request
     * @return
     */
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
