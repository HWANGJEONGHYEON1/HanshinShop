package com.hanshin.shop.service;

import com.hanshin.shop.entity.goods.Goods;
import com.hanshin.shop.entity.order.OrderDetailDto;
import com.hanshin.shop.entity.order.OrderDto;
import com.hanshin.shop.entity.order.OrderGoodsVO;
import com.hanshin.shop.entity.order.OrderVO;
import com.hanshin.shop.entity.user.User;
import com.hanshin.shop.repository.CartMapper;
import com.hanshin.shop.repository.GoodsMapper;
import com.hanshin.shop.repository.OrderMapper;
import com.hanshin.shop.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final GoodsMapper goodsMapper;
    private final UserMapper userMapper;
    private final CartMapper cartMapper;

    public OrderVO findOne(Long id) {
        final OrderVO one = orderMapper.selectOne(id);
        return one;
    }

    public List<OrderDetailDto> findOrderDetails(Long userId) {
        return orderMapper.findOrderDetails(userId);
    }

    @Transactional
    public Long insert(List<OrderDto> orderDtoList, Long userId) {
        final User user = userMapper.findById(userId);
        List<OrderGoodsVO> orderGoodsVOList = new ArrayList<>();

        for (OrderDto dto : orderDtoList) {
            final Goods goods = goodsMapper.findGoodsOne(dto.getGoodsId());
            OrderGoodsVO orderGoodsVO = OrderGoodsVO.createOrderGoods(goods.getId(), goods.getPrice(), dto.getAmount());
            orderGoodsVOList.add(orderGoodsVO);
        }
        OrderVO order = OrderVO.createOrder(user.getId(), user.getAddress(), orderGoodsVOList);
        orderMapper.insert(order);
        cartMapper.deleteAll(userId);
        saveOrderGoodsItem(orderGoodsVOList, order);

        if (Objects.isNull(order.getId())) {
            throw new IllegalStateException("주문이 완료되지 않았습니다.");
        }

        return order.getId();
    }

    @Transactional
    public int orderCancel(Long userId) {
        OrderVO orderVO = orderMapper.selectOne(userId);
        orderVO.cancel();
        log.info("# orderVO {}", orderVO);
        return orderMapper.cancel(orderVO.getId(), orderVO.getState());
    }

    private void saveOrderGoodsItem(List<OrderGoodsVO> orderGoodsVOList, OrderVO order) {
        for (OrderGoodsVO orderGoods : orderGoodsVOList) {
            orderGoods.receiveOrderId(order.getId());
            orderMapper.insertOrderGoods(orderGoods);
        }
    }
}
