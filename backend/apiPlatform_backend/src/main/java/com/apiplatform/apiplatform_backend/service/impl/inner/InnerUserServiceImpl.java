package com.apiplatform.apiplatform_backend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.apiplatform.apiplatform_backend.common.ErrorCode;
import com.apiplatform.apiplatform_backend.exception.BusinessException;
import com.apiplatform.apiplatform_backend.mapper.UserMapper;
import com.apiplatform.apiplatform_common.model.entity.User;
import com.apiplatform.apiplatform_common.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
