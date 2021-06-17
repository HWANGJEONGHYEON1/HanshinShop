package com.hanshin.shop.service;

import com.hanshin.shop.vo.order.*;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.repository.CartMapper;
import com.hanshin.shop.repository.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;

    public OrderVO selectByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    public int orderCount(Long userId) {
        return orderMapper.orderCount(userId);
    }

    public List<OrderDetailDto> findOrderDetails(Long userId) {
        return orderMapper.findOrderDetails(userId);
    }

    @Transactional
    public void insert(List<OrderDto> orderDtoList, User user) {

        List<OrderGoodsVO> orderGoodsVOList = new ArrayList<>();

        orderDtoList.stream()
                .map(OrderGoodsVO::createOrderGoods)
                .forEach(orderGoodsVO -> orderGoodsVOList.add(orderGoodsVO));

        OrderVO order = OrderVO.createOrder(user.getId(), user.getAddress(), orderGoodsVOList);
        orderMapper.insert(order);
        saveOrderGoodsItem(orderGoodsVOList, order);

        cartMapper.deleteAll(user.getId());

        if (Objects.isNull(order.getId())) {
            throw new IllegalStateException("주문이 완료되지 않았습니다.");
        }
    }

    @Transactional
    public void orderCancel(Long userId) {
        OrderVO orderVO = orderMapper.selectByUserId(userId);
        if (orderVO.getState() == OrderStatus.CANCEL) {
            throw new IllegalStateException("이미 주문 취소 되었습니다.");
        }
        orderVO.cancel();
    }

    private void saveOrderGoodsItem(List<OrderGoodsVO> orderGoodsVOList, OrderVO order) {
        for (OrderGoodsVO orderGoods : orderGoodsVOList) {
            orderGoods.receiveOrderId(order.getId());
            orderMapper.insertOrderGoods(orderGoods);
        }
    }
}
