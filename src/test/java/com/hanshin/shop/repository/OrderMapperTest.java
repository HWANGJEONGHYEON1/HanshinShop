package com.hanshin.shop.repository;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.order.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class OrderMapperTest extends IntegrationTests {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    private Long goodsId = 1L;
    private Long userId = 1L;

    @BeforeEach
    @DisplayName("주문 데이터 삽입, orderDto -> orderGoods -> orderVO")
    void setup() {
        OrderVO orderVO = new OrderVO(userId, goodsId, OrderStatus.ORDER);
        Goods goods = goodsMapper.findOne(goodsId);
        log.info("# goods : {}", goods);
        OrderDto orderDto = new OrderDto(goods.getId(), goods.getPrice(), 10);
        OrderGoodsVO orderGoods = OrderGoodsVO.createOrderGoods(orderDto);
        orderMapper.order(orderVO);
        orderGoods.receiveOrderId(orderVO.getId());
        log.info("# orderVO {}", orderVO);
        orderMapper.insertOrderGoods(orderGoods);
    }

    @Test
    @DisplayName("주문 성공 시 주문 상태(ORDER)")
    @Transactional
    void order() {
        OrderVO orderVO = orderMapper.selectByOrderId(1L);
        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(orderVO.getState());
    }

    @Test
    @DisplayName("주문 취소시 주문 상태(CANCEL)")
    @Transactional
    void order_cancel() {
        OrderVO orderVO = new OrderVO(userId, goodsId, OrderStatus.CANCEL);
        orderMapper.cancel(orderVO);
        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(orderVO.getState());
    }

    @Test
    @DisplayName("유저의 order 개수")
    void order_count() {
        List<OrderDetailDto> orderDetails = orderMapper.findOrderDetails(userId);
        log.info("# {}", orderDetails);
        Assertions.assertThat(1).isEqualTo(orderMapper.orderCount(userId));
    }
}
