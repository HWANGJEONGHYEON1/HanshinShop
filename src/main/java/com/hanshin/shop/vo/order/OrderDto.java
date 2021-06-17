package com.hanshin.shop.vo.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderDto {

    private Long goodsId;
    private int price;
    private int amount;

}
