package com.example.model;

import com.example.model.dto.TransferDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name= "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;
    @ManyToOne
    @JoinColumn(name= "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;
    @Column(name="transfer_amount", precision = 10, scale = 0, nullable = false)
    @NotNull(message = "Enter the amount you want to transfer")
    @DecimalMin(value = "10000", message = "Minimum transfer amount is 10,000 VNĐ")
    @DecimalMax(value = "1000000000", message = "Maximum transfer amount is 1.000.000.000 VNĐ")
    private BigDecimal transferAmount;
    private long fees;
    @Column(name="fees_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal feesAmount;
    @Column(name="total_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal totalAmount;

    public TransferDTO transferDTO(){
        return new TransferDTO()
                .setTransferAmount(String.valueOf(transferAmount))
                .setFeesAmount(String.valueOf(feesAmount))
                .setTotalAmount(String.valueOf(totalAmount))
                .setSenderId(String.valueOf(sender.getId()))
                .setSenderName(String.valueOf(sender.getFullName()))
                .setRecipientId(String.valueOf(recipient.getId()))
                .setRecipientName(String.valueOf(recipient.getFullName()))
                ;
    }
}
