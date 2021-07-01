package com.hanshin.shop.service;

import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.goods.GoodsDto;
import com.hanshin.shop.vo.order.*;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.repository.GoodsMapper;
import com.hanshin.shop.repository.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class OrderServiceTest {

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

        user = userMapper.findById(18L);
        //when
        orderId = orderService.insert(dtoList, user);
    }

    @Test
    public void 상품주문() throws Exception {

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
    public void 주문조회() throws Exception {
        //given
        //when
        final OrderVO one = orderService.selectByOrderId(orderId);

        //then
        Assertions.assertThat(one.getState()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    public void 주문취소_예외() throws Exception {
        orderService.orderCancel(orderId);
        Assertions.assertThatThrownBy(() -> orderService.orderCancel(orderId))
                .isInstanceOf(IllegalStateException.class);
    }


    @Test
    public void 주문취소() throws Exception {
        System.out.println("# orderID : " + orderId);
        //given
        final OrderVO one = orderService.selectByOrderId(orderId);
        //when
        orderService.orderCancel(orderId);
        //then
        Assertions.assertThat(one.getState()).isEqualTo(OrderStatus.CANCEL);
    }

}