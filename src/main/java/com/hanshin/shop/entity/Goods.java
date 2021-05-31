package com.hanshin.shop.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Long categoryId;

    private int price;

    private String description;

    private int reviewCount;

    private int discountRate;

    private Date regDate;

    private List<GoodsAttachVO> attachList;

    public static Goods create(GoodsDto goodsDto) {
        Goods goods = new Goods();
        goods.setName(goodsDto.getName());
        goods.setPrice(goodsDto.getPrice());
        goods.setDescription(goodsDto.getDescription());

        return goods;
    }
}
