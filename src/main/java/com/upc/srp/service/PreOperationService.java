package com.upc.srp.service;

import java.util.List;
import java.util.Map;

/**
 * @author by anbang
 * @description TODO
 * @date 2019/12/26 16:26
 */

public interface PreOperationService {

    public Map selectbyno(String no);
    public List<Map> selectjiben();
    public int insertdata(Map data);
    public List<Map> selectbynolist(List<String> no);

}