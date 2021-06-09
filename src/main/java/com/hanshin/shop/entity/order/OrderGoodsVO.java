package com.hanshin.shop.entity.order;

import com.hanshin.shop.entity.goods.Goods;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
//@RequiredArgsConstructor(staticName = "of")
public class OrderGoodsVO {

    private Long id;

    private Long goodsId;

    private Long orderId;

    private int orderPrice;

    private int amount;

    public static OrderGoodsVO createOrderGoods(Long goodsId, int orderPrice, int count) {
        return OrderGoodsVO.builder()
                .goodsId(goodsId)
                .orderPrice(orderPrice)
                .amount(count)
                .build();
    }

    public int getTotalPrice() {
        return getOrderPrice() * getAmount();
    }

    public void receiveOrderId(Long id) {
        this.orderId = id;
    }


}
