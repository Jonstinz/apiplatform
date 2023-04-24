package com.apiplatform.apiplatform_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.apiplatform.apiplatform_common.model.entity.InterfaceInfo;

/**
 *
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
