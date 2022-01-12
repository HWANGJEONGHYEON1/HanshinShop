package com.hanshin.shop.repository;

import com.hanshin.shop.vo.cart.CartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {

    void save(CartVO cartVO);

    int count(Long userId);

    List<CartVO> findAll(long userId);

    int update(@Param("amount") int amount, @Param("id") Long id);

    int delete(Long id);

    int deleteAll(Long userId);

    CartVO isExistCartOne(@Param("goodsId") Long goodsId, @Param("userId") Long userId);
}
