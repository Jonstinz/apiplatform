package com.apiplatform.apiplatform_gateway.config;

import com.alibaba.fastjson.JSONObject;
import com.apiplatform.apiplatform_gateway.Model.beans.ApiRoute;
import com.apiplatform.apiplatform_gateway.Service.ApiRouteService;
import com.apiplatform.apiplatform_gateway.Service.impl.CacheRouteDefinitionRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author zjh
 * @version 1.0
 */
@Service
public class RouteHandler implements ApplicationEventPublisherAware, CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RouteHandler.class);

    private ApplicationEventPublisher publisher;

    @Autowired
    private ApiRouteService apiRouteService;

    @Autowired
    private CacheRouteDefinitionRepository cacheRouteDefinitionRepository;



    @Override
    public void run(String... args) throws Exception {
        log.info("首次初始化路由....");
        this.loadRouteConfig();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

/**
 * 它首先从缓存中获取所有已有的路由定义，然后从数据库中获取所有的 API 路由配置。
 *  接着，它遍历 API 路由配置列表，如果某个 API 路由配置的 delete 字段为 true，并且该路由定义已经存在于缓存中，那么它会删除该路由；
 *  否则，它将根据该 API 路由配置创建一个新的路由定义，并将其存储到缓存中。
 *  最后，它通过发布 RefreshRoutesEvent 事件通知 Gateway 路由刷新，以便将新的路由配置生效。
 */

    public void loadRouteConfig() {
        log.info("加载路由配置...");

        Flux<RouteDefinition> definitionFlux = cacheRouteDefinitionRepository.getRouteDefinitions();
        new Thread(() -> {
            List<String> existRouteIds = definitionFlux.toStream().map(RouteDefinition::getId).collect(Collectors.toList());

            List<ApiRoute> apiRouteList = apiRouteService.findAll();
            if (apiRouteList != null && apiRouteList.size() > 0) {
                apiRouteList.forEach(a -> {
                    if (BooleanUtils.isTrue(a.getDelete()) && existRouteIds.contains(a.getRouteId())) {
                        deleteRoute(a.getRouteId());
                    } else {
                        RouteDefinition routeDefinition = a.parseToRoute();
                        System.out.println("s: " + JSONObject.toJSONString(routeDefinition));
                        if (routeDefinition != null) {
                            cacheRouteDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
                        }
                    }
                });
            }

            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        }).start();

    }

    public void deleteRoute(String routeId) {
        log.info("删除路由：" + routeId);
        cacheRouteDefinitionRepository.delete(Mono.just(routeId)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
