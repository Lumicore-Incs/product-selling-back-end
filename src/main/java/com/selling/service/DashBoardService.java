package com.selling.service;

import com.selling.dto.get.ExcelTypeDto;

import java.util.List;

public interface DashBoardService {
    List<ExcelTypeDto> findOrder();

    List<ExcelTypeDto> ConformOrder();

}
