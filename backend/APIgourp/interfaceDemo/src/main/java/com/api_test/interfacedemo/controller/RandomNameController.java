package com.api_test.interfacedemo.controller;

import com.api_test.interfacedemo.RandomName;
import org.springframework.web.bind.annotation.*;


/**
 * 随机用户名 API
 *
 * @author zjh
 */
@RestController
@RequestMapping("/randomname")
public class RandomNameController {


    @GetMapping("/test")
    public String testByGet() {
        String result = "Normal response";
        return result;
    }
    @GetMapping("")
    public String getRandomnameByGet() {
        String result = "生成随机名字：" + RandomName.getRandomname();
        return result;
    }

}
