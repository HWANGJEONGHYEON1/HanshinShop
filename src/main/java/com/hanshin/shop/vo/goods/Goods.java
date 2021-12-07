package com.hanshin.shop.vo.goods;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods {

    private Long id;

    private String name;

    private Long categoryId;

    private int price;

    private String description;

    private int reviewCount;

    private int discountRate;

    private Date regDate;

    private List<GoodsAttachVO> attachList = new ArrayList<>();

    private Goods(GoodsDto dto) {
        this.name = dto.getName();
        this.categoryId = dto.getCategoryId();
        this.price = dto.getPrice();
        this.description = dto.getDescription();
    }

    public static Goods create(GoodsDto goodsDto) {
        return new Goods(goodsDto);
    }

    public void addAttach(GoodsAttachVO goodsAttachVO) {
        attachList.add(goodsAttachVO);
    }

}
