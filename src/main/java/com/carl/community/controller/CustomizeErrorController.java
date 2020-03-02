package com.carl.community.controller;

import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaoq
 * @date 2020/3/2 18:45
 */
@ControllerAdvice
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomizeErrorController implements ErrorController {

    @ExceptionHandler(Exception.class)
    String handleControllerException(HttpServletRequest request, Throwable ex, Model model) {
        HttpStatus status = getStatus(request);
        model.addAttribute("status", status);
        if (ex instanceof CustomizeException) {
            model.addAttribute("message", ex.getMessage());
        } else {
            model.addAttribute("message", ErrorMessage.SERVER_ERROR);
        }
        return "error";
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("error");
        HttpStatus status = getStatus(request);
        mv.addObject("status", status);
        if (status.is4xxClientError()) {
            mv.addObject("message", ErrorMessage.CLIENT_ERROR.getMessage());
        } else if (status.is5xxServerError()) {
            mv.addObject("message", ErrorMessage.SERVER_ERROR.getMessage());
        }
        return mv;
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
