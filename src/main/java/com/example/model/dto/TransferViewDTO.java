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
@Accessors (chain = true)
public class TransferViewDTO {
    public Long id;
    public Long senderId;
    public String senderName;
    public Long recipientId;
    public String recipientName;
    public BigDecimal transferAmount;
    public BigDecimal feesAmount;
    public BigDecimal totalAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm:ss dd-MM-yyyy ", timezone = "Asia/Ho_Chi_Minh")
    public Date createAt;

}
