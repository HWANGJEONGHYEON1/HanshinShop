package com.hanshin.shop.repository;

import com.hanshin.shop.entity.goods.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    Goods findOne(Long id);

    void save(Goods goods);

    Goods findGoodsOne(Long id);

    List<Goods> findAllList();

    List<Goods> findListOfCategory(Long categoryId);
}
