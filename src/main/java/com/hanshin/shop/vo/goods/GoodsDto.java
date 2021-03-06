package com.hanshin.shop.vo.goods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoodsDto {

    private String name;

    private int price;

    private String description;

    private Long categoryId;

}
