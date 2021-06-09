package com.hanshin.shop.entity.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    private Long id;

    private Long userId;

    private Long goodsId;

    private OrderStatus state;

    private String address;

    private List<OrderGoodsVO> orderGoodsList;

    private Date orderDate;

    public static OrderVO createOrder(Long userId, String addr, List<OrderGoodsVO> orderGoods) {
        return OrderVO.builder()
                .userId(userId)
                .state(OrderStatus.ORDER)
                .address(addr)
                .orderGoodsList(orderGoods)
                .build();
    }

    public void cancel() {
        this.state = OrderStatus.CANCEL;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        totalPrice = orderGoodsList.stream()
                .mapToInt(OrderGoodsVO::getTotalPrice)
                .sum();

        return totalPrice;
    }
}