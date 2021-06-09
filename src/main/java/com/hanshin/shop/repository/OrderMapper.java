package com.hanshin.shop.repository;

import com.hanshin.shop.entity.order.OrderDetailDto;
import com.hanshin.shop.entity.order.OrderGoodsVO;
import com.hanshin.shop.entity.order.OrderStatus;
import com.hanshin.shop.entity.order.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    void insert(OrderVO orderVO);

    void insertOrderGoods(OrderGoodsVO orderGoodsVO);

    OrderVO selectOne(Long id);

    int cancel(@Param("id") Long id, @Param("state") OrderStatus orderStatus);

    List<OrderDetailDto> findOrderDetails(Long userId);
}
