package com.upc.srp.service;

import java.util.List;
import java.util.Map;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2020/1/5 13:42
 */
public interface CTService {
    /**
     * @description 查找CT
     * @param params
     * @return list
     * @author zhoutao
     * @date 2020/1/5 13:43
     */
    public List<Map<String, Object>> getCT(Map<String, Object> params);
}
