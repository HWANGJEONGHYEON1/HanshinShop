package com.hanshin.shop.entity.order;

import com.hanshin.shop.entity.goods.Goods;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
//@RequiredArgsConstructor(staticName = "of")
public class OrderGoods {

    private Long id;

    private Goods goods;

    private Order order;

    private int orderPrice;

    private int amount;

    public static OrderGoods createOrder(Goods goods, int orderPrice, int count) {
        return OrderGoods.builder()
                .goods(goods)
                .orderPrice(orderPrice)
                .amount(count)
                .build();
    }

    public int getTotalPrice() {
        return getOrderPrice() * getAmount();
    }

}
