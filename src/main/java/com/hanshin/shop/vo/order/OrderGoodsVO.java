package com.hanshin.shop.vo.order;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@ToString
//@RequiredArgsConstructor(staticName = "of")
public class OrderGoodsVO {

    private Long id;

    private Long goodsId;

    private Long orderId;

    private int orderPrice;

    private int amount;

    public static OrderGoodsVO createOrderGoods(OrderDto orderDto) {
        return OrderGoodsVO.builder()
                .goodsId(orderDto.getGoodsId())
                .orderPrice(orderDto.getPrice())
                .amount(orderDto.getAmount())
                .build();
    }

    public int getTotalPrice() {
        return getOrderPrice() * getAmount();
    }

    public void receiveOrderId(Long id) {
        this.orderId = id;
    }


}
