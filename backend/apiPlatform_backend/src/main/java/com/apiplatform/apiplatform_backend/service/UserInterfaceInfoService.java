package com.apiplatform.apiplatform_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.apiplatform.apiplatform_common.model.entity.UserInterfaceInfo;

/**
 *
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 从数据库中查询用户是否有剩余的接口调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeLeftNum(long interfaceInfoId, long userId);

    UserInterfaceInfo invokeleftNum(long interfaceInfoId, long userId);
}
