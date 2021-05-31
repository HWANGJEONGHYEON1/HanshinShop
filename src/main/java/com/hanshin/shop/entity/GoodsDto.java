package com.hanshin.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoodsDto {

    private String name;

    private int price;

    private String description;
}
