package com.selling.service.impl;

import com.selling.dto.get.ExcelTypeDto;
import com.selling.model.Customer;
import com.selling.repository.CustomerRepo;
import com.selling.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private final CustomerRepo customerRepo;

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
}
