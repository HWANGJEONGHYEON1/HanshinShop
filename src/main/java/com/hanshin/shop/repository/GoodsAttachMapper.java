package com.hanshin.shop.repository;

import com.hanshin.shop.entity.goods.GoodsAttachVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsAttachMapper {

    void insert(GoodsAttachVO vo);

    void delete(String uuid);

    List<GoodsAttachVO> findByGoodsId(Long goodsId);
}
