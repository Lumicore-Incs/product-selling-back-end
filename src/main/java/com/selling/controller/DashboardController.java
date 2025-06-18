package com.selling.controller;

import com.selling.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final OrderService orderService;

  public void updateOrderDetails() {
      orderService.updateOrderDetails();
  }
}
