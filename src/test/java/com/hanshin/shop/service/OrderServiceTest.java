package com.hanshin.shop.service;

import com.hanshin.shop.entity.goods.Goods;
import com.hanshin.shop.entity.goods.GoodsDto;
import com.hanshin.shop.entity.order.*;
import com.hanshin.shop.repository.GoodsMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsMapper goodsMapper;

    Long testUserId = 21L;

    @Test
    public void 상품주문() throws Exception {

        final Long goodsId = createGoods();
        OrderDto dto = new OrderDto(goodsId, 1);
        List<OrderDto> dtoList = Arrays.asList(dto);
        //when
        final Long insertId = orderService.insert(dtoList, 1L);
        final OrderVO orderedVO = orderService.findOne(insertId);
        //then
        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(orderedVO.getState());
        Assertions.assertThat("suwon").isEqualTo(orderedVO.getAddress());

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
        Long id = 19L;
        //when
        final OrderVO one = orderService.findOne(id);

        //then
        Assertions.assertThat(one.getState()).isEqualTo(OrderStatus.ORDER);
    }


    @Test
    public void 주문_상세_내역() throws Exception {

        //when
        final List<OrderDetailDto> orderDetails = orderService.findOrderDetails(21L);
        //then

        Assertions.assertThat(orderDetails.size()).isEqualTo(2);
        Assertions.assertThat(orderDetails.get(0).getState()).isEqualTo(OrderStatus.ORDER);
    }


    @Test
    public void 주문취소() throws Exception {
        //when
        final int updateData = orderService.orderCancel(testUserId);
        //then
        Assertions.assertThat(updateData).isEqualTo(1);

    }

}