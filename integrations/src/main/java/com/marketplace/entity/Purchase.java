package com.marketplace.entity;

import com.marketplace.constant.OrderStatus;
import lombok.Data;

import java.util.Date;

@Data
public class Purchase {
    Long id;
    Double price;
    String item;
    String name;
    String instruction;
    Long productId;
    OrderStatus status;
    User buyer;
    User seller;
    Date date;
}
