package com.apiplatform.apiplatform_gateway.Service;

import com.apiplatform.apiplatform_gateway.Model.beans.ApiRoute;

import java.util.List;

public interface ApiRouteService {

    List<ApiRoute> findAll();

    boolean saveOrUpdate(ApiRoute route);

    boolean delete(ApiRoute route);

    ApiRoute findByRouteId(String routeId);

    ApiRoute findById(Integer id);
}
