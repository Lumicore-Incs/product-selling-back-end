package com.selling.dto;

import com.selling.model.Customer;
import com.selling.model.OrderDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDto {
    private Integer orderId;
    private String serialNo;
    private BigDecimal totalPrice;
    private LocalDateTime date;
    private String trackingId;
    private String status;
    private String remark;
    private Integer customerId;
}
