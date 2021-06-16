package com.hanshin.shop.entity.goods;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
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

    private Goods(GoodsDto dto) {
        this.name = dto.getName();;
        this.categoryId = dto.getCategoryId();
        this.price = dto.getPrice();
        this.description = dto.getDescription();
    }

    public static Goods create(GoodsDto goodsDto) {
        return new Goods(goodsDto);
    }



}
