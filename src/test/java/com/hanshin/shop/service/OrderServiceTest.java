package com.hanshin.shop.service;

import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.goods.GoodsDto;
import com.hanshin.shop.vo.order.*;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.repository.GoodsMapper;
import com.hanshin.shop.repository.UserMapper;
import org.assertj.core.api.Assertions;
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

    Long testUserId = 21L;

    @Test
    public void 상품주문() throws Exception {

        final Long goodsId = createGoods();

        OrderDto dto = new OrderDto(goodsId, 1000, 1);
        List<OrderDto> dtoList = Arrays.asList(dto);

        final User user = userMapper.findById(18L);
        //when
        orderService.insert(dtoList, user);
        final OrderVO orderedVO = orderService.selectByUserId(18L);
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
        Long id = 22L;
        //when
        final OrderVO one = orderService.selectByUserId(id);

        //then
        Assertions.assertThat(one.getState()).isEqualTo(OrderStatus.ORDER);
    }


    @Test
    public void 주문_상세_내역() throws Exception {

        //when
        final List<OrderDetailDto> orderDetails = orderService.findOrderDetails(21L);
        //then

        Assertions.assertThat(orderDetails.size()).isEqualTo(2);
        Assertions.assertThat(orderDetails.get(0).getState()).isEqualTo(OrderStatus.CANCEL);
    }


    @Test
    public void 주문취소_예외() throws Exception {
        Assertions.assertThatThrownBy(() -> orderService.orderCancel(21L))
                .isInstanceOf(IllegalStateException.class);
    }


    @Test
    public void 주문취소() throws Exception {
        //given
        final OrderVO one = orderService.selectByUserId(22L);
        //when
        orderService.orderCancel(22L);
        //then
        Assertions.assertThat(one.getState()).isEqualTo(OrderStatus.CANCEL);
    }

}