package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class DepositViewDTO {
    public Long id;
    public Long customerId;
    public String customerName;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm:ss dd-MM-yyyy ", timezone = "Asia/Ho_Chi_Minh")
    public Date createdAt;
    public BigDecimal transactionAmount;

}
