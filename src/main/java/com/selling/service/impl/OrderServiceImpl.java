package com.selling.service.impl;

import com.selling.dto.CustomerDto;
import com.selling.dto.ProductDto;
import com.selling.dto.UserDto;
import com.selling.dto.get.CustomerDtoGet;
import com.selling.dto.get.OrderDetailsDtoGet;
import com.selling.dto.get.OrderDtoGet;
import com.selling.model.*;
import com.selling.repository.OrderDetailsRepo;
import com.selling.repository.OrderRepo;
import com.selling.service.OrderService;
import com.selling.util.ModelMapperConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepo orderRepo;
    private final OrderDetailsRepo orderDetailsRepo;
    private final ModelMapperConfig modelMapperConfig;

    @Override
    public List<OrderDtoGet> getAllOrder() {
        List<OrderDtoGet> customerDtoGetList = new ArrayList<>();
        List<Order> allCustomer = orderRepo.findAll();

        // Get today's date (without time)
        LocalDate today = LocalDate.now();

        for (Order order : allCustomer) {
            // Convert order date to LocalDate (if stored as LocalDateTime)
            LocalDate orderDate = order.getDate().toLocalDate();

            // Check if year, month, and day match
            if (orderDate.equals(today)) {
                customerDtoGetList.add(entityToOrderDtoGet(order));
            }
        }
        return customerDtoGetList;
    }


    @Override
    public List<OrderDtoGet> getAllCOrderByUserId(UserDto userDto) {
        List<OrderDtoGet> orderDtoGetList = new ArrayList<>();

        LocalDate today = LocalDate.now();
        List<Order> userOrders = orderRepo.findByCustomerUser(dtoToUserEntity(userDto));

        for (Order order : userOrders) {
            LocalDate orderDate = order.getDate().toLocalDate();

            if (orderDate.equals(today)) {
                orderDtoGetList.add(entityToOrderDtoGet(order));
            }
        }

        return orderDtoGetList;
    }


    private List<OrderDetailsDtoGet> getOrderDetailsData(Order orderId) {
        List<OrderDetails> byOrder = orderDetailsRepo.findByOrder(orderId);
        List<OrderDetailsDtoGet> orderDetailsDtoGetList = new ArrayList<>();
        for (OrderDetails orderDetails : byOrder) {
            orderDetailsDtoGetList.add(entityToOrderDetails(orderDetails));
        }
        return orderDetailsDtoGetList;
    }


    //mapper -------------
    private User dtoToUserEntity(UserDto userDto) {
        return (userDto == null) ? null : modelMapperConfig.modelMapper().map(userDto, User.class);
    }


    private OrderDtoGet entityToOrderDtoGet(Order order) {
        if (order.getCustomer() != null) {
            OrderDtoGet map = modelMapperConfig.modelMapper().map(order, OrderDtoGet.class);
            map.setCustomerId(entityToCustomer(order.getCustomer()));
            map.setOrderDetails(getOrderDetailsData(order));
            return map;
        }
        return null;
    }


    private OrderDetailsDtoGet entityToOrderDetails(OrderDetails orderDetails) {
        if (orderDetails != null) {
            OrderDetailsDtoGet map = modelMapperConfig.modelMapper().map(orderDetails, OrderDetailsDtoGet.class);
            map.setProductId(entityToProduct(orderDetails.getProduct()));
            return map;
        } else {
            return null;
        }
    }

    private ProductDto entityToProduct(Product product) {
        return (product == null) ? null : modelMapperConfig.modelMapper().map(product, ProductDto.class);
    }

    private CustomerDto entityToCustomer(Customer customer) {
        return modelMapperConfig.modelMapper().map(customer, CustomerDto.class);
    }


}
