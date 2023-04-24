package com.apiplatform.apiplatform_gateway.Controller;

import com.apiplatform.apiplatform_gateway.Model.beans.ApiRoute;
import com.apiplatform.apiplatform_gateway.Model.beans.JsonResult;
import com.apiplatform.apiplatform_gateway.Service.ApiRouteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zjh
 * @version 1.0
 */
@RestController
@RequestMapping("api/route")
public class ApiRouteAction {
    @Autowired
    private ApiRouteService apiRouteService;

    @GetMapping("/list")
    public JsonResult list() {
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.put("routeList", apiRouteService.findAll());
        return jsonResult;
    }

    @PostMapping("/add")
    public JsonResult save( @RequestBody ApiRoute route) {
        if (route == null || StringUtils.isBlank(route.getRouteId())) {
            return new JsonResult(false, "id不能为空");
        } else if (StringUtils.isBlank(route.getUri())) {
            return new JsonResult(false, "uri不能为空");
        }

        ApiRoute sameRouteIdObj = apiRouteService.findByRouteId(route.getRouteId());
        if (sameRouteIdObj != null && sameRouteIdObj.getId() != null) {
            if (route.getId() == null) {
                return new JsonResult(false, "已存在相同 RouteId 的配置");
            }
        }
        route.setPredicates(route.getPredicates() != null ? route.getPredicates().trim() : null);
        route.setFilters(route.getFilters() != null ? route.getFilters().trim() : null);

        boolean res = apiRouteService.saveOrUpdate(route);
        return new JsonResult(res, res ? "操作成功" : "操作失败");
    }

    @PostMapping("/update")
    public JsonResult update( @RequestBody ApiRoute route) {
        if (route == null || StringUtils.isBlank(route.getRouteId())) {
            return new JsonResult(false, "id不能为空");
        } else if (StringUtils.isBlank(route.getUri())) {
            return new JsonResult(false, "uri不能为空");
        }

        ApiRoute oldRoute = null;
        if (route.getId() != null) {
            oldRoute = apiRouteService.findById(route.getId());
            if (oldRoute == null || oldRoute.getId() == null) {
                return new JsonResult(false, "数据不存在或已被删除");
            }
        }

        ApiRoute sameRouteIdObj = apiRouteService.findByRouteId(route.getRouteId());
        if (sameRouteIdObj != null && sameRouteIdObj.getId() != null) {
            if (route.getId() == null) {
                return new JsonResult(false, "已存在相同 RouteId 的配置");
            }
        }
        route.setPredicates(route.getPredicates() != null ? route.getPredicates().trim() : null);
        route.setFilters(route.getFilters() != null ? route.getFilters().trim() : null);

        boolean res = apiRouteService.saveOrUpdate(route);
        return new JsonResult(res, res ? "操作成功" : "操作失败");
    }

    @DeleteMapping( "/adds/{routeId}")
    public JsonResult delete(@PathVariable("routeId") String routeId) {
        ApiRoute route = apiRouteService.findByRouteId(routeId);
        if (route == null || StringUtils.isBlank(route.getRouteId())) {
            return new JsonResult(false, "路由不存在");
        }

        boolean res = apiRouteService.delete(route);
        return new JsonResult(res, res ? "操作成功" : "操作失败");
    }
}
