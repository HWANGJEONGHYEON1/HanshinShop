package com.hanshin.shop.controller.order;

import com.hanshin.shop.vo.order.OrderDetailDto;
import com.hanshin.shop.vo.order.OrderDto;
import com.hanshin.shop.vo.user.LoginUser;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order/count")
    public int count(@LoginUser User user) {
        return orderService.orderCount(user.getId());
    }

    @PostMapping("/order")
    public void order(@RequestBody List<OrderDto> dtoList, @LoginUser User user) {
        orderService.insert(dtoList, user);
    }

    @GetMapping("/order/details")
    public List<OrderDetailDto> orderDetails(@LoginUser User user) {
        Long userId = user.getId();
        final List<OrderDetailDto> orderDetails = orderService.findOrderDetails(userId);
        return orderDetails;
    }

    @PatchMapping("/order/cancel/{id}")
    public void cancel(@PathVariable Long id) {
        orderService.orderCancel(id);
    }


}
