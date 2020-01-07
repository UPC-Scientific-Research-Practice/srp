package com.upc.srp.service.impl;


import com.upc.srp.dao.PreDao;
import com.upc.srp.service.PreOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author by anbang
 * @description TODO
 * @date 2019/12/26 16:26
 */

@Service
public class PreOperationServiceImpl implements PreOperationService {

    @Autowired
    private PreDao preDao;

    @Override
    public List<Map<String, Object>> selectbyno(Map<String, Object> params){
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
        return preDao.selectbyno(params);
    }

    @Override
    public int insertdata(Map<String, Object> data){
        return preDao.insertdata(data);
    }

    @Override
    public List<Map<String, Object>> selectjiben(Map<String, Object> params){
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
        return preDao.selectjiben(params);
    }

    @Override
    public List<Map<String, Object>> selectbynolist(List<String> no){
        return preDao.selectbynolist(no);
    }

    @Override
    public int updatejiben(Map<String, Object> params){ return preDao.updatejiben(params);}

    @Override
    public int updateall(Map<String, Object> params){ return preDao.updateall(params);}



}
