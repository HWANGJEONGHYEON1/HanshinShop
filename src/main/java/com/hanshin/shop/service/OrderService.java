package com.hanshin.shop.service;

import com.hanshin.shop.vo.order.*;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.repository.CartMapper;
import com.hanshin.shop.repository.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;

    public OrderVO selectByOrderId(Long orderId) {
        return orderMapper.selectByOrderId(orderId);
    }

    public int orderCount(Long userId) {
        return orderMapper.orderCount(userId);
    }

    public List<OrderDetailDto> findOrderDetails(Long userId) {
        return orderMapper.findOrderDetails(userId);
    }

    @Transactional
    public Long insert(List<OrderDto> orderDtoList, User user) {

        List<OrderGoodsVO> orderGoodsVOList = getOrderDtoList(orderDtoList);

        OrderVO order = OrderVO.createOrder(user.getId(), user.getAddress(), orderGoodsVOList);
        orderMapper.insert(order);
        saveOrderGoodsItem(orderGoodsVOList, order);

        cartMapper.deleteAll(user.getId());

        return order.getId();
    }

    private List<OrderGoodsVO> getOrderDtoList(List<OrderDto> orderDtoList) {
        return orderDtoList.stream()
                .map(OrderGoodsVO::createOrderGoods)
                .collect(Collectors.toList());
    }

    @Transactional
    public void orderCancel(Long orderId) {
        OrderVO orderVO = orderMapper.selectByOrderId(orderId);
        if (orderVO.getState() == OrderStatus.CANCEL) {
            throw new IllegalStateException("이미 주문 취소 되었습니다.");
        }
        orderVO.cancel();
        orderMapper.cancel(orderVO);
    }

    private void saveOrderGoodsItem(List<OrderGoodsVO> orderGoodsVOList, OrderVO order) {
        for (OrderGoodsVO orderGoods : orderGoodsVOList) {
            orderGoods.receiveOrderId(order.getId());
            orderMapper.insertOrderGoods(orderGoods);
        }
    }
}
