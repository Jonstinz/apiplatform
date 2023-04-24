package com.api_test.interfacedemo;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.apiplatform.apiplatformclient.model.City;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Weather {


    public static JSONObject getCity(){
        String cityjs="https://j.i8tq.com/weather2020/search/city.js";
        //发送请求获取数据
        String cityStr = HttpUtil.createGet(cityjs).header("Referer", "http://www.weather.com.cn/").execute().body();
        //需要解析数据
        String cityJson = cityStr.replace("var city_data =", "");
        //创建集合存储数据
        //使用 NAMECN:AREAID
        Map<String, Long> map = new HashMap<>();
        JSONObject entries = JSONUtil.parseObj(cityJson);
        Set<String> keys = entries.keySet();
        for (String key : keys) {
//            System.out.println("第一层 省" + key);
            Set<String> citys = entries.getJSONObject(key).keySet();
            for (String city : citys) {
//                System.out.println("--第二层 城市" + city);
                Set<String> areas = entries.getJSONObject(key).getJSONObject(city).keySet();
                for (String area : areas) {
//                    System.out.println("---第三层 区:" + area);
                    JSONObject jsonObject = entries.getJSONObject(key).getJSONObject(city).getJSONObject(area);
//                    System.out.println(jsonObject.toString());
                    map.put(jsonObject.getStr("NAMECN"), jsonObject.getLong("AREAID"));
                }
            }
        }
        //把map数据转换成json
        String jsonStr = JSONUtil.toJsonStr(map);
//        System.out.println(jsonStr);
        return JSONUtil.parseObj(map);
    }


    public static JSONObject getRealWeather(City city) throws IOException {
        //获取出所有城市的AreaId
        JSONObject cityAreaId = getCity();
        //取出对应城市的areaid
        String areaId = cityAreaId.getStr(city.getCity());
        //实时天气的地址
        String REALWEATHER_URL="http://d1.weather.com.cn/sk_2d/";
        if (areaId==null){
            throw new RuntimeException("该城市找不到AreaId");
        }
        //拼接请求地址
       String uri=REALWEATHER_URL+areaId+".html";

        Document doc = Jsoup.connect(uri)
                .referrer("http://d1.weather.com.cn/sk_2d/")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(8000)
                .get();


        //替换数据不然会影响json的解析
//        System.out.printf("查詢結果："+doc.text().replace("var dataSK=",""));
        String json = doc.text().replace("var dataSK=","");
        return JSONUtil.parseObj(json);
    }


}
