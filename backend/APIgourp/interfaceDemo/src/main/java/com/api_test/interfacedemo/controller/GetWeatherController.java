package com.api_test.interfacedemo.controller;


import cn.hutool.json.JSONObject;
import com.api_test.interfacedemo.Weather;
import com.apiplatform.apiplatformclient.model.City;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
/**
 * 获取当日天气 API
 *
 * @author zjh
 */
@RestController
@RequestMapping("/getweather")
public class GetWeatherController {

    @GetMapping("/test")
    public String testByGet() {
        String result = "Normal response";
        return result;
    }
    @GetMapping("")
    public String getWeatherByGet(@RequestBody City city) throws IOException {
        JSONObject realWeather = Weather.getRealWeather(city);
        return (realWeather.getStr("cityname")
                + " 天气：" + realWeather.getStr("weather")
                + " 温度:" + realWeather.getStr("temp")
                + " 湿度:" + realWeather.getStr("sd")
                + " Pm2.5程度:" + realWeather.getStr("aqi_pm25")
        );
    }

}
