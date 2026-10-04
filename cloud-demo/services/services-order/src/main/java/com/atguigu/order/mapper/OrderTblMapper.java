package com.atguigu.order.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface OrderTblMapper {
    int insert(Map<String, Object> params);

}
