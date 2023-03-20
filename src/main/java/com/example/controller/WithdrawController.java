package com.example.controller;

import com.example.model.Customer;
import com.example.model.Withdraw;
import com.example.service.customer.ICustomerService;
import com.example.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("customers/withdraw")
public class WithdrawController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IWithdrawService withdrawService;
    @GetMapping("/{id}")
    public String showWithdrawPage(@PathVariable("id") String customerId, Model model){
        long id = Long.parseLong(customerId);
        Optional<Customer> customerOptional = customerService.findById(id);
        if(!customerOptional.isPresent()){
            return "404";
        }
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customerOptional.get());
        model.addAttribute("withdraw", withdraw);
        return "customer/withdraw";
    }
    @PostMapping("/{id}")
    public String withdraw(@PathVariable Long id, @Validated @ModelAttribute Withdraw withdraw, BindingResult bindingResult, Model model){
        Optional<Customer> customerOptional = customerService.findById(id);
        if(!customerOptional.isPresent()){
            return "404";
        }
        if(bindingResult.hasFieldErrors()){
            model.addAttribute("errors", true);
            return "customer/withdraw";
        }
        Customer customer = customerOptional.get();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        if(currentBalance.compareTo(transactionAmount) == 1){
            BigDecimal newBalance = currentBalance.subtract(transactionAmount);

            customer.setBalance(newBalance);
            withdraw.setCustomer(customer);

            customerService.save(customer);
            withdrawService.save(withdraw);
            model.addAttribute("message", "Successful transaction");
        } else if (currentBalance.compareTo(transactionAmount) == 0 ) {
            model.addAttribute("error", "The balance must be greater than the amount you want to withdraw");
        } else{
            model.addAttribute("error", "Your balance is not enough to make this transaction");
        }
        withdraw.setTransactionAmount(BigDecimal.ZERO);
        model.addAttribute("withdraw", withdraw);

        return "customer/withdraw";
    }
}
