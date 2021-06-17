package com.hanshin.shop.repository;

import com.hanshin.shop.vo.goods.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select id, name from category")
    List<CategoryVO> list();

    @Select("select id, name from category where id = #{id}")
    CategoryVO get(Long id);
}
