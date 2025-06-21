package com.selling.repository;

import com.selling.dto.get.ExcelTypeDto;
import com.selling.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByUserId(Long id);

    @Query("SELECT new com.selling.dto.get.ExcelTypeDto(" +
            "c.customerId, c.name, c.address, c.contact01, c.contact02, od.qty) " +
            "FROM Customer c " +
            "JOIN c.orders o " +
            "JOIN o.orderDetails od " +
            "WHERE c.status = 'pending'" +
            "ORDER BY od.qty asc ")
    List<ExcelTypeDto> findPendingOrdersWithQuantities();

    int countByUserId(Long id);

    List<Customer> findByUser_Id(Long id);
}
