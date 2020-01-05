package com.upc.srp.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author by zhoutao
 * @description 病历Dao
 * @date 2020/1/3 16:01
 */
@Mapper
public interface CTDao {

    int getCTCount(Map<String, Object> params);

    List<Map<String, Object>> getCT(Map<String, Object> params);

    int insertCT(Map<String, Object> params);
}
