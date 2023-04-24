package com.apiplatform.apiplatform_gateway.Model.dao;

import com.apiplatform.apiplatform_gateway.Model.beans.ApiRoute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper
public interface ApiRouteDAO {

    @Select("select * from api_route")
    List<ApiRoute> findAll();

    @Select("select * from api_route where routeId = #{routeId} AND `delete` = 0 LIMIT 1")
    ApiRoute findByRouteId(String routeId);

    @Select("select * from api_route where id = #{id} AND `delete` = 0")
    ApiRoute findById(Integer id);

    boolean update(ApiRoute route);

    boolean insert(ApiRoute route);

    boolean delete(ApiRoute route);

}
