package com.kxdmmr.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 自定义错误页面覆盖spring boot中的错误页面
 * @author kxdmmr
 *
 */
@Controller
public class ErrorController {

    @GetMapping("/401")
    public String badRequest() {
        return "error/401";
    }

    @GetMapping("/404")
    public String notFound() {
        return "error/404";
    }

    @GetMapping("/500")
    public String serverError() {
        return "error/500";
    }
}