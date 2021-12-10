package com.hanshin.shop.vo.order;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class OrderVO {

    private Long id;

    private Long userId;

    private Long goodsId;

    private OrderStatus state;

    private String address;

    private List<OrderGoodsVO> orderGoodsList;

    private Date orderDate;

    public OrderVO(Long userId, Long goodsId, OrderStatus orderStatus) {
        this.userId = userId;
        this.goodsId = goodsId;
        this.state = orderStatus;
    }

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