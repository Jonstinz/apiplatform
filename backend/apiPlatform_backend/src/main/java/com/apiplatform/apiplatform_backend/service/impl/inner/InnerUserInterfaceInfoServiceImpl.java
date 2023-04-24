package com.apiplatform.apiplatform_backend.service.impl.inner;

import com.apiplatform.apiplatform_backend.service.UserInterfaceInfoService;
import com.apiplatform.apiplatform_common.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
    @Override
    public boolean invokeLeftNum(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeLeftNum(interfaceInfoId, userId);
    }

}
