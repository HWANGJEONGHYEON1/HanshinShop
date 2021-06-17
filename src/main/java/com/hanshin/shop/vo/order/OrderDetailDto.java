package com.hanshin.shop.vo.order;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class OrderDetailDto {

    private String uuid;
    private String uploadPath;
    private String fileName;

    private String name;
    private int orderPrice;
    private int amount;
    private String address;
    private OrderStatus state;
    private LocalDateTime orderDate;
}
