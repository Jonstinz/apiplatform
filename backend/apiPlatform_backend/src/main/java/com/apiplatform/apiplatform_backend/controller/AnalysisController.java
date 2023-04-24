package com.apiplatform.apiplatform_backend.controller;

import com.apiplatform.apiplatform_backend.model.vo.UserInterfaceVO;
import com.apiplatform.apiplatform_backend.service.UserService;
import com.apiplatform.apiplatform_common.model.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.apiplatform.apiplatform_backend.annotation.AuthCheck;
import com.apiplatform.apiplatform_backend.common.BaseResponse;
import com.apiplatform.apiplatform_backend.common.ErrorCode;
import com.apiplatform.apiplatform_backend.common.ResultUtils;
import com.apiplatform.apiplatform_backend.exception.BusinessException;
import com.apiplatform.apiplatform_backend.mapper.UserInterfaceInfoMapper;
import com.apiplatform.apiplatform_backend.model.vo.InterfaceInfoVO;
import com.apiplatform.apiplatform_backend.service.InterfaceInfoService;
import com.apiplatform.apiplatform_common.model.entity.InterfaceInfo;
import com.apiplatform.apiplatform_common.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析控制器
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOList);
    }

    @GetMapping("/top/interface/user")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<UserInterfaceVO>> listTopUserInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopUserInvokeInterfaceInfo(5);
        Map<Long, List<UserInterfaceInfo>> userIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getUserId));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIdObjMap.keySet());
        List<User> list = userService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //list为userId的信息
        //todo 只需返回用户信息即可
        List<UserInterfaceVO> interfaceInfoVOList = list.stream().map(user -> {
            UserInterfaceVO userVO = new UserInterfaceVO();
            BeanUtils.copyProperties(user, userVO);
            int totalNum = userIdObjMap.get(user.getId()).get(0).getTotalNum();
            userVO.setTotalNum(totalNum);
            return userVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOList);
    }
}
