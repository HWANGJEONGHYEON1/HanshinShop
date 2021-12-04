package com.hanshin.shop.service;

import com.hanshin.shop.vo.cart.CartDTO;
import com.hanshin.shop.vo.cart.CartVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    CartService cartService;
    @Autowired
    GoodsService goodsService;

    Long goodsId = 27L;
    Long userId = 1L;
    CartVO cartVO;

    @BeforeEach
    void setup() {
        // 상품
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void 상품_인서트() throws Exception {
        insert(1);
    }

    private void insert(int amount) {
        CartDTO dto = new CartDTO(userId, goodsId, amount);
        cartVO = CartVO.save(dto);
        cartService.insert(cartVO);
    }

    @Test
    @WithMockUser(username = "member", roles = "MEMBER")
    public void 장바구니_삭제() {
        //given
        insert(1);
        //when
        final CartVO existCartOne = cartService.isExistCartOne(goodsId, userId);
        final int deleteCount = cartService.delete(existCartOne.getId());
        //then
        Assertions.assertThat(deleteCount).isEqualTo(1);
    }


    @Test
    @WithMockUser(username = "member", roles = "MEMBER")
    public void 추가하려는_상품이_존재하는지() throws Exception {
        //given
        insert(3);
        //when
        CartVO existCartOne = cartService.isExistCartOne(goodsId, userId);

        //then
        Assertions.assertThat(existCartOne.getAmount()).isEqualTo(3);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void 전체_카트_조회() throws Exception {
        //when
        Long userId = 19L;
        //then
        cartService.findAll(userId);
        Assertions.assertThat(cartService.findAll(userId).size()).isEqualTo(1);
    }
}