package com.njzhxt.annotationsDemo.service.Impl;

import com.njzhxt.annotationsDemo.service.ParamProviderService;
import org.springframework.stereotype.Service;

/**
 * @Author: ybc
 * Date: 2020/9/28 18:21
 * Content:
 */
@Service
public class ParamProviderServiceImpl implements ParamProviderService {
    @Override
    public String getParamValueByKey(String paramKey) {
        System.out.println("======="+paramKey+"=======");
        return paramKey;
    }
}
