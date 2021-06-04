package com.hanshin.shop.repository;

import com.hanshin.shop.entity.cart.CartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    void insert(CartVO cartVO);

    List<CartVO> findAll(long id);

    int update(int amount, Long id);

    int delete(Long id);

    int deleteAll(Long userId);

    CartVO isExistCartOne(Long goodsId, Long userId);
}
