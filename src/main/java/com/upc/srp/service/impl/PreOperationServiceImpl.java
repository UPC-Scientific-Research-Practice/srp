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

    public Map selectbyno(String no){

        return preDao.selectbyno(no);
    }

    public int insertdata(Map data){

        return preDao.insertdata(data);
    }

    public List<Map> selectjiben(){

        return preDao.selectjiben();
    }

    public List<Map> selectbynolist(List<String> no){
        return preDao.selectbynolist(no);
    }



}
