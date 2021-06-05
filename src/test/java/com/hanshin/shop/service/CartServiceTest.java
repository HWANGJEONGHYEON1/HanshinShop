package com.hanshin.shop.service;

import com.hanshin.shop.entity.cart.CartDTO;
import com.hanshin.shop.entity.cart.CartVO;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;
    CartVO cartVO;

    @Test
    public void 장바구니_삭제() throws Exception {
        //given
        Long id = 1L;
        //when
        final int deleteCount = cartService.delete(id);
        //then
        Assertions.assertThat(deleteCount).isEqualTo(1);
    }


    @Test
    public void 추가하려는_상품이_존재하는지() throws Exception {
        //given
        Long goodsId = 30L;
        Long userId = 19L;
        CartDTO dto = new CartDTO(19L, 30L, 10);
        //when
        final CartVO existCartOne = cartService.isExistCartOne(goodsId, userId);
        final CartVO save = CartVO.save(dto);
        //then
        Assertions.assertThat(existCartOne.getAmount()).isEqualTo(save.getAmount());
    }


    @Test
    public void 해당_상품_존재시_update() throws Exception {
        //given
        Long goodsId = 30L;
        Long userId = 19L;
        CartDTO dto = new CartDTO(19L, 30L, 11);
        //when
        cartService.insert(CartVO.save(dto));
        //then
        Assertions.assertThat(cartService.isExistCartOne(goodsId, userId).getAmount()).isEqualTo(11);
    }

    @Test
    public void 상품_인서트() throws Exception {
        CartDTO dto = new CartDTO(19L, 30L, 10);
        cartVO = CartVO.save(dto);
        //when
        cartService.insert(cartVO);
        //then
    }

    @Test
    public void 전체_카트_조회() throws Exception {
        //when
        Long userId = 19L;
        //then
        cartService.findAll(userId);
        Assertions.assertThat(cartService.findAll(userId).size()).isEqualTo(1);
    }

}