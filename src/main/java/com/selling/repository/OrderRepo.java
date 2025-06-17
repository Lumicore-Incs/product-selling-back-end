package com.selling.repository;

import com.selling.model.Order;
import com.selling.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    @EntityGraph(attributePaths = {"customer", "orderDetails", "orderDetails.product"})
    List<Order> findByCustomerUser(User userId);

    List<Order> findAllByOrderByOrderIdDesc();
}
