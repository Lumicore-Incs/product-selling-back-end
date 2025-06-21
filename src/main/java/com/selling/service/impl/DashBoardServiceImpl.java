package com.selling.service.impl;

import com.selling.dto.UserDto;
import com.selling.dto.get.ExcelTypeDto;
import com.selling.model.Customer;
import com.selling.repository.CustomerRepo;
import com.selling.repository.OrderRepo;
import com.selling.repository.UserRepo;
import com.selling.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<ExcelTypeDto> findOrder() {
        return customerRepo.findPendingOrdersWithQuantities();
    }

    @Override
    public List<ExcelTypeDto> ConformOrder() {
        List<ExcelTypeDto> pendingOrdersWithQuantities = customerRepo.findPendingOrdersWithQuantities();
        for (ExcelTypeDto excelTypeDto : pendingOrdersWithQuantities) {
            Optional<Customer> byId = customerRepo.findById(excelTypeDto.getId());
            if (byId.isPresent()) {
                byId.get().setStatus("active");
                customerRepo.save(byId.get());
            }
        }
        return pendingOrdersWithQuantities;
    }

    @Override
    public int getTotalOrder(UserDto user) {
        if (user.getRole().equals("admin") || user.getRole().equals("ADMIN") || user.getRole().equals("Admin")) {
            return (int) customerRepo.count();
        }
        return customerRepo.countByUserId(user.getId());
    }

    @Override
    public int getTodayOrder(UserDto user) {
        if (user.getRole().equals("admin") || user.getRole().equals("ADMIN") || user.getRole().equals("Admin")) {

        }
        return 0;
    }

    @Override
    public int getConformOrder(UserDto user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        if (user.getRole().equals("admin") || user.getRole().equals("ADMIN") || user.getRole().equals("Admin")) {
            return orderRepo.countByStatusAndDateBetween("Deliver", startOfMonth, now);
        }
        return orderRepo.countByCustomerUserEmailAndStatusAndDateBetween(
                user.getEmail(), "Deliver", startOfMonth, now);
    }

    @Override
    public int getCancelOrder(UserDto user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        String cancelStatus = "Failed to Deliver";

        if (user.getRole().equalsIgnoreCase("admin")) {
            return orderRepo.countByStatusAndDateBetween(cancelStatus, startOfMonth, now);
        } else {
            return orderRepo.countByCustomerUserEmailAndStatusAndDateBetween(
                    user.getEmail(), cancelStatus, startOfMonth, now);
        }
    }
}
