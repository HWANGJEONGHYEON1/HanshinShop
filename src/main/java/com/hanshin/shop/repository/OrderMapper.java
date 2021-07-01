package com.hanshin.shop.repository;

import com.hanshin.shop.vo.order.OrderDetailDto;
import com.hanshin.shop.vo.order.OrderGoodsVO;
import com.hanshin.shop.vo.order.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderMapper {

    Long insert(OrderVO orderVO);

    void insertOrderGoods(OrderGoodsVO orderGoodsVO);

    OrderVO selectByOrderId(Long orderId);

    int cancel(OrderVO orderVO);

    List<OrderDetailDto> findOrderDetails(Long userId);

    int orderCount(Long userId);
}
