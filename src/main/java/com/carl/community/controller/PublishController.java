package com.carl.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhaoq
 */
@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
}
