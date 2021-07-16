package com.hanshin.shop.vo.cart;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class CartDTO {

    private Long userId;
    private Long goodsId;
    private Integer amount;
}
