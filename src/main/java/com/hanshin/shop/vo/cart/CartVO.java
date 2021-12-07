package com.hanshin.shop.vo.cart;

import com.hanshin.shop.vo.goods.GoodsAttachVO;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class CartVO {

    private Long id;

    private Long userId;

    private Long goodsId;

    private String name;

    private int price;

    private int amount;

    private List<GoodsAttachVO> attachList;

    private CartVO(CartDTO dto) {
        this.userId = dto.getUserId();
        this.goodsId = dto.getGoodsId();
        this.amount = dto.getAmount();
    }

    public static CartVO save(CartDTO dto) {
        return new CartVO(dto);
    }
}
