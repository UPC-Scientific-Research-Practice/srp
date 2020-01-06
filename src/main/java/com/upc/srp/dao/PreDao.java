package com.upc.srp.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author by anbang
 * @description TODO
 * @date 2019/12/26 16:55
 */

@Mapper
public interface PreDao {

    public List<Map<String, Object>> selectbyno(Map<String, Object> params);
    public List<Map<String, Object>> selectjiben(Map<String, Object> params);
    public int insertdata(Map<String, Object> params);
    public List<Map<String, Object>> selectbynolist(List<String> no);
    public int updatejiben(Map<String, Object> params);
    public int updateall(Map<String, Object> params);
}
