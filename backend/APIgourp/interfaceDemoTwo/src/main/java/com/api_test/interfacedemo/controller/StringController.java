package com.api_test.interfacedemo.controller;


import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 返回字符串 API
 *
 * @author zjh
 */
@RestController
@RequestMapping("/string")
public class StringController {

    @GetMapping("/test")
    public String testByGet() {
        String result = "Normal response";
        return result;
    }

    @PostMapping("")
    public String getStringByPost(@RequestBody String str) {

        String result = "输入的字符串是：" + str;
        return result;
    }

}
