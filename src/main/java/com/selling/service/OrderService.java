package com.selling.service;

import com.selling.dto.UserDto;
import com.selling.dto.get.CustomerDtoGet;
import com.selling.dto.get.OrderDtoGet;

import java.util.List;

public interface OrderService {
    List<OrderDtoGet> getAllOrder();

    List<OrderDtoGet> getAllCOrderByUserId(UserDto userDto);
}
