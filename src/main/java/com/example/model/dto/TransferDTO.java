package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class TransferDTO {
    public String senderId;
    public String senderName;
    public String recipientId;
    public String recipientName;
    public String transferAmount;
    public String feesAmount;
    public String totalAmount;

    public TransferDTO(Long senderId, String senderName, Long recipientId, String recipientName, BigDecimal transferAmount, BigDecimal feesAmount, BigDecimal totalAmount) {
        this.senderId = senderId.toString();
        this.senderName = senderName;
        this.recipientId = recipientId.toString();
        this.recipientName = recipientName;
        this.transferAmount = transferAmount.toString();
        this.feesAmount = feesAmount.toString();
        this.totalAmount = totalAmount.toString();
    }
}
