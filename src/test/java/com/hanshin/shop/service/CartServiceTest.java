package com.hanshin.shop.service;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.vo.cart.CartDTO;
import com.hanshin.shop.vo.cart.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
class CartServiceTest extends IntegrationTests {

    @Autowired
    CartService cartService;
    @Autowired
    GoodsService goodsService;

    CartVO cartVO;

    @BeforeEach
    void setup() {
        // 상품
    }

    @Test
    @WithMockUser(username = "admin", roles = "MEMBER")
    @DisplayName("장바구니에 담긴 상품의 개수 조회")
    @Order(2)
    void get_cart_of_number() {
        Assertions.assertThat(2).isEqualTo(cartService.numberOfCart(1L));
    }

    @Test
    @Transactional
    @DisplayName("장바구니에 상품을 넣는다.")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void cart_add() throws Exception {
        insert();
        CartVO existCartOne = cartService.isExistCartOne(2L, 2L);
        Assertions.assertThat(2L).isEqualTo(existCartOne.getGoodsId());
        Assertions.assertThat("샤인머스켓").isEqualTo(existCartOne.getName());
    }

    private void insert() {
        CartDTO dto = new CartDTO(2L, 1L, 10);
        cartVO = CartVO.save(dto);
        cartService.add(cartVO);
    }

    @Test
    @DisplayName("카트 아이디로 장바구니 삭제")
    @Transactional
    @WithMockUser(username = "member", roles = "MEMBER")
    void cart_delete_one() {
        log.debug("# cart_delete_one ");
        insert();
        //when
        int delete = cartService.delete(1L);
        //then
        Assertions.assertThat(1).isEqualTo(delete);
    }

    @Test
    @DisplayName("카트 전체 삭제")
    @Transactional
    @WithMockUser(username = "admin", roles = "ADMIN")
    void cart_delete_all() {
        insert();
        cartService.deleteAll(2L);
        Assertions.assertThat(0).isEqualTo(cartService.findAll(2L).size());
    }

    @Test
    @DisplayName("전체 장바구니 목록 조회")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN")
    void cart_list_all() throws Exception {
        //then
        List<CartVO> carts = cartService.findAll(1L);
        Assertions.assertThat(2).isEqualTo(carts.size());
    }
}