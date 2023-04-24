package com.apiplatform.apiplatform_common.service;

/**
 *
 */
public interface InnerUserInterfaceInfoService {

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

}
