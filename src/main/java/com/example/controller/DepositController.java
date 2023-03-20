package com.example.controller;

import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.service.customer.ICustomerService;
import com.example.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("customers/deposit")
public class DepositController {
    @Autowired
    private IDepositService depositService;
    @Autowired
    private ICustomerService customerService;
    @GetMapping("/{id}")
    public String showDepositPage(@PathVariable("id") String customerId, Model model){
        try{
            long id = Long.parseLong(customerId);
            Optional<Customer> customerOptional = customerService.findById(id);
            if (customerOptional.isPresent()) {
                Deposit deposit = new Deposit();
                deposit.setCustomer(customerOptional.get());
                model.addAttribute("deposit", deposit);
                return "customer/deposit";
            }
            return "404";
        } catch (Exception e){
            return "404";
        }
    }
    @PostMapping("/{id}")
    public String deposit(@PathVariable Long id, @Validated @ModelAttribute Deposit deposit, BindingResult bindingResult, Model model){
        Optional<Customer> customerOptional = customerService.findById(id);
        if(bindingResult.hasFieldErrors()){
            model.addAttribute("errors", true);
            return "customer/deposit";
        }
        if(!customerOptional.isPresent()){
            return "404";
        }

        Customer customer = customerOptional.get();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        BigDecimal newBalance = currentBalance.add(transactionAmount);

        customer.setBalance(newBalance);
        deposit.setCustomer(customer);

        customerService.deposit(deposit);

        deposit.setTransactionAmount(BigDecimal.ZERO);
        model.addAttribute("deposit", deposit);
        model.addAttribute("message", "Deposit transaction successful!!!");
        return "customer/deposit";
    }
}
