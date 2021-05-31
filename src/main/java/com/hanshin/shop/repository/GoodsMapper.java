package com.hanshin.shop.repository;

import com.hanshin.shop.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    Goods findOne(Long id);

    void save(Goods goods);

    List<Goods> findAllList();

    List<Goods> findListOfCategory(Long categoryId);
}
