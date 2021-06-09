package com.hanshin.shop.controller.order;

import com.hanshin.shop.entity.order.OrderDetailDto;
import com.hanshin.shop.entity.order.OrderDto;
import com.hanshin.shop.entity.user.LoginUser;
import com.hanshin.shop.entity.user.User;
import com.hanshin.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")
    public String shopDetail() {
        return "order/order-goods";
    }

    @GetMapping("/orderCount")
    @ResponseBody
    public int count(@LoginUser User user) {
        final List<OrderDetailDto> orderDetails = orderService.findOrderDetails(user.getId());
        return orderDetails.size();
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<String> order(@RequestBody List<OrderDto> dtoList, @LoginUser User user) {
        log.info("dto {} ", dtoList);
        orderService.insert(dtoList, user.getId());

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/order-details")
    @ResponseBody
    public ResponseEntity<List<OrderDetailDto>> orderDetails(@LoginUser User user) {
        Long userId = user.getId();
        final List<OrderDetailDto> orderDetails = orderService.findOrderDetails(userId);
        log.info("orderDetails : " + orderDetails);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @PatchMapping("/order/cancel")
    @ResponseBody
    public ResponseEntity<String> cancel(@LoginUser User user) {
        log.info("patchMapping");
        final int updateCount = orderService.orderCancel(user.getId());
        if (updateCount != 1) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);

    }


}
