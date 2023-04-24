package com.apiplatform.apiplatform_gateway.config;

import com.apiplatform.apiplatform_gateway.Model.beans.ApiRoute;
import com.apiplatform.apiplatform_gateway.Service.ApiRouteService;
import com.apiplatform.apiplatform_gateway.Service.RouteDefinitionCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zjh
 * @version 1.0
 */
@Component
public class StartListener {

    private static final Logger logger = LoggerFactory.getLogger(StartListener.class);

    @Autowired
    private RouteDefinitionCacheService cacheService;

    @Autowired
    private ApiRouteService routeService;

    @PostConstruct
    public void init() {
        logger.info("初始化路由数据...");
        List<ApiRoute> routeList = routeService.findAll();
        if (routeList != null && routeList.size() > 0) {
            cacheService.saveAll(routeList.stream().map(ApiRoute::parseToRoute).collect(Collectors.toList()));
        }
    }
}
