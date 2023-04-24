package com.apiplatform.apiplatform_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apiplatform.apiplatform_common.model.entity.UserInterfaceInfo;

import java.util.List;

/**
 * @Entity com.yupi.project.model.entity.UserInterfaceInfo
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {


    /**
     * 被调用最多的接口TOP5
     * @param limit
     * @return
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

    /**
     * 最活跃的TOP5用户
     * @param limit
     * @return
     */
    List<UserInterfaceInfo> listTopUserInvokeInterfaceInfo(int limit);
}




