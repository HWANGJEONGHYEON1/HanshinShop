package com.hanshin.shop.vo.cart;

import com.hanshin.shop.vo.goods.GoodsAttachVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CartVO {

    private Long id;

    private Long userId;

    private Long goodsId;

    private String goodsName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartVO cartVO = (CartVO) o;
        return price == cartVO.price && amount == cartVO.amount && Objects.equals(id, cartVO.id) && Objects.equals(userId, cartVO.userId) && Objects.equals(goodsId, cartVO.goodsId) && Objects.equals(goodsName, cartVO.goodsName) && Objects.equals(attachList, cartVO.attachList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, goodsId, goodsName, price, amount, attachList);
    }
}
