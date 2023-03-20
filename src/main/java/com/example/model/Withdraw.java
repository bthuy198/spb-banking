package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "withdraws")
public class Withdraw extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name= "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @Column(name="transaction_amount", precision = 10, scale = 0, nullable = false)
    @NotNull(message = "Enter the amount you want to withdraw")
    @DecimalMin(value = "10000", message = "Minimum withdraw amount is 10,000 VNĐ")
    @DecimalMax(value = "1000000000", message = "Maximum withdraw amount is 1.000.000.000 VNĐ")
    private BigDecimal transactionAmount;
}
