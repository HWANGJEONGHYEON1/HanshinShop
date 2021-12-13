package com.hanshin.shop.repository;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.vo.cart.CartDTO;
import com.hanshin.shop.vo.cart.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class CartMapperTests extends IntegrationTests {

    @Autowired
    CartMapper cartMapper;

    Long userId = 2L;
    Long goodsId = 2L;

    @Test
    @DisplayName("카트에 담기 - data-h2.sql에 이미 카트에 하나 넣어놓음 -> 사이즈 2")
    @Transactional
    void cart() {
        CartDTO cartDTO = new CartDTO(userId, goodsId, 10);
        cartMapper.save(CartVO.save(cartDTO));
        Assertions.assertThat(2).isEqualTo(cartMapper.count(userId));
    }

    @Test
    @DisplayName("전체 카트 리스트 사이즈")
    void cart_list_by_user() {
        Assertions.assertThat(1).isEqualTo(cartMapper.findAll(userId).size());
    }

    @ParameterizedTest
    @DisplayName("cart 수량 업데이트")
    @ValueSource(ints = {2,4,6,7,10})
    void cart_update_by_amount(int amount) {
        CartVO cart = cartMapper.isExistCartOne(goodsId, userId);
        log.info("# cart {}", cart);
        cartMapper.update(amount, cart.getId());
        Assertions.assertThat(amount).isEqualTo(cartMapper.isExistCartOne(goodsId, userId).getAmount());
    }

    @Test
    @DisplayName("cart id로 삭제, 유저정보가 1인 카트는 2개 -> 삭제하면 1개")
    void cart_delete_by_id() {
        cartMapper.delete(1L);
        Assertions.assertThat(1).isEqualTo(cartMapper.findAll(1L).size());
    }

    @Test
    @DisplayName("카트 전체 삭제")
    void cart_delete_all() {
        cartMapper.deleteAll(1L);
        Assertions.assertThat(0).isEqualTo(cartMapper.findAll(1L).size());
    }
}
