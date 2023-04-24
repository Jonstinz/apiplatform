package com.apiplatform.apiplatform_gateway.Service.impl;

import com.apiplatform.apiplatform_gateway.Model.beans.ApiRoute;
import com.apiplatform.apiplatform_gateway.Model.dao.ApiRouteDAO;
import com.apiplatform.apiplatform_gateway.Service.ApiRouteService;
import com.apiplatform.apiplatform_gateway.Service.RouteDefinitionCacheService;
import com.apiplatform.apiplatform_gateway.config.RouteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApiRouteServiceImpl implements ApiRouteService {

    private static final Logger logger = LoggerFactory.getLogger(ApiRouteService.class);

    @Autowired
    private ApiRouteDAO apiRouteDAO;

    @Autowired
    private RouteDefinitionCacheService cacheService;

    @Autowired
    private RouteHandler routeHandler;

    @Override
    public List<ApiRoute> findAll() {
        return apiRouteDAO.findAll();
    }

    @Override
    public boolean saveOrUpdate( ApiRoute route) {
        route.setUpdateTime(new Date());
        ApiRoute oldRoute = apiRouteDAO.findById(route.getId());
        boolean res = false;
        if (oldRoute != null && oldRoute.getId() != null) {
            res = apiRouteDAO.update(route);
        } else {
            res = apiRouteDAO.insert(route);
        }

        if (res) {
            logger.info("更新缓存，通知网关重新加载路由信息...");
            cacheService.save(route.parseToRoute());
            routeHandler.loadRouteConfig();
        }

        return res;
    }

    @Override
    public boolean delete(ApiRoute route) {
        route.setUpdateTime(new Date());
        boolean res = apiRouteDAO.delete(route);
        if (res) {
            logger.info("更新缓存，通知网关重新加载路由信息...");
            cacheService.save(route.parseToRoute());
            routeHandler.loadRouteConfig();
        }
        return res;
    }

    @Override
    public ApiRoute findByRouteId(String routeId) {
        return apiRouteDAO.findByRouteId(routeId);
    }

    @Override
    public ApiRoute findById(Integer id) {
        return apiRouteDAO.findById(id);
    }
}
