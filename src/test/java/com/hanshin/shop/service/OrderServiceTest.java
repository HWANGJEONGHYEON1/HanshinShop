package com.hanshin.shop.service;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.goods.GoodsDto;
import com.hanshin.shop.vo.order.*;
import com.hanshin.shop.vo.user.RoleType;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.repository.GoodsMapper;
import com.hanshin.shop.repository.UserMapper;
import com.hanshin.shop.vo.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class OrderServiceTest extends IntegrationTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserMapper userMapper;

    private List<OrderDto> dtoList;
    private User user;
    private Long orderId;

    @BeforeEach
    public void setup() {
        final Long goodsId = createGoods();

        OrderDto dto = new OrderDto(goodsId, 1000, 1);
        dtoList = Arrays.asList(dto);

        user = userMapper.findById(1L);
        //when
        orderId = orderService.insert(dtoList, user);
    }


    @Test
    @DisplayName("상품주문 했을시 OrderStatus 값 확인")
    @Transactional(readOnly = true)
    public void goods_order() {

        final OrderVO orderedVO = orderService.selectByOrderId(orderId);
        //then
        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(orderedVO.getState());

    }

    private Long createGoods() {
        GoodsDto goodsDto = new GoodsDto("test", 1000, "test", 1L);
        Goods goods = Goods.create(goodsDto);
        goodsMapper.save(goods);
        return goods.getId();
    }


    @Test
    @DisplayName("주문을 이미 취소했을 때, 다시 주문 취소햇을 때 에러발생")
    public void order_cancel_exception() {
        orderService.orderCancel(orderId);
        Assertions.assertThatThrownBy(() -> orderService.orderCancel(orderId))
                .isInstanceOf(IllegalStateException.class);
    }


    @Test
    @DisplayName("주문 취소 시 OrderStatus.CANCEL")
    public void order_cancel() {
        //given
        final OrderVO one = orderService.selectByOrderId(orderId);
        //when
        orderService.orderCancel(orderId);
        //then
        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(one.getState());
    }
}