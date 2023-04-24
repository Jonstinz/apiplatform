package com.apiplatform.apiplatform_gateway.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.apiplatform.apiplatform_gateway.Service.RouteDefinitionCacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由信息二级缓存
 * @author zjh
 * @version 1.0
 */
@Service
public class RouteDefinitionCacheServiceImpl implements RouteDefinitionCacheService {

    /**
     * 本次缓存
     */
    private static ConcurrentHashMap<String, RouteDefinition> definitionMap = new ConcurrentHashMap<>();

    /**
     * redis 缓存地址
     */
    public static String SPACE = "API_routes" ;


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> list = new ArrayList<>();
        if (definitionMap.size() > 0) {
            return new ArrayList<>(definitionMap.values());
        } else {
            redisTemplate.opsForHash().values(SPACE)
                    .stream().forEach(r -> {
                        RouteDefinition route = JSONObject.parseObject(r.toString(), RouteDefinition.class);
                list.add(route);
                definitionMap.put(route.getId(), route);
            });
            return list;
        }
    }

    @Override
    public boolean saveAll(List<RouteDefinition> definitions) {
        if (definitions != null && definitions.size() > 0) {
            definitions.forEach(this::save);
            return true;
        }
        return false;
    }

    @Override
    public boolean has(String routeId) {
        return definitionMap.containsKey(routeId) ? true : redisTemplate.opsForHash().hasKey(SPACE, routeId);
    }

    @Override
    public boolean delete(String routeId) {
        if (has(routeId)) {
            definitionMap.remove(routeId);
            redisTemplate.opsForHash().delete(SPACE, routeId);
            return true;
        }
        return false;
    }

    @Override
    public boolean save(RouteDefinition r) {
        if (r != null && StringUtils.isNotBlank(r.getId())) {
            definitionMap.put(r.getId(), r);
            redisTemplate.opsForHash().put(SPACE, r.getId(), JSONObject.toJSONString(r));
            return true;
        }
        return false;
    }
}
