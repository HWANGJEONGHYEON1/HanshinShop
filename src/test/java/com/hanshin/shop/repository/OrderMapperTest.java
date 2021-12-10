package com.hanshin.shop.repository;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.vo.order.OrderStatus;
import com.hanshin.shop.vo.order.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class OrderMapperTest extends IntegrationTests {

    @Autowired
    private OrderMapper orderMapper;
    private OrderVO orderVO;

    @BeforeEach
    void setup() {
        orderVO = new OrderVO(1L, 1L, OrderStatus.ORDER);
        orderMapper.order(orderVO);
    }

    @Test
    @DisplayName("주문 성공")
    void order() {
        OrderVO orderVO = orderMapper.selectByOrderId(1L);
        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(orderVO.getState());
    }
}
