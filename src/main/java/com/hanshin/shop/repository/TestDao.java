package com.hanshin.shop.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestDao {

    @Select("select now()")
    public String getTest();

    public String selectTestName();

}
