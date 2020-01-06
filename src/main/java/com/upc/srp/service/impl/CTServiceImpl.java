package com.upc.srp.service.impl;

import com.upc.srp.dao.CTDao;
import com.upc.srp.service.CTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2020/1/5 13:44
 */
@Service
public class CTServiceImpl implements CTService {

    @Autowired
    CTDao ctDao;

    /**
     * @param params
     * @return list
     * @description 查找CT
     * @author zhoutao
     * @date 2020/1/5 13:43
     */
    @Override
    public List<Map<String, Object>> getCT(Map<String, Object> params) {
        if(params.get("page") == null){
            params.put("page", 1);
        }
        if(params.get("limit") == null){
            params.put("limit", 10);
        }
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = (page - 1) * limit;
        params.put("start", start);
        return ctDao.getCT(params);
    }
}
