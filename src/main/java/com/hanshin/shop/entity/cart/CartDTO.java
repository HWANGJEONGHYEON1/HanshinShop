package com.hanshin.shop.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartDTO {

    private Long userId;
    private Long goodsId;
    private int amount;
}
