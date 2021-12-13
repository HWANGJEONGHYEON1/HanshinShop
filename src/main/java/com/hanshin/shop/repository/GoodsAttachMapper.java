package com.hanshin.shop.repository;

import com.hanshin.shop.vo.goods.GoodsAttachVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsAttachMapper {

    void insert(GoodsAttachVO vo);

    List<GoodsAttachVO> findByGoodsId(Long goodsId);
}
