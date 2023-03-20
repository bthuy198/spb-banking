package com.example.controller;

import com.example.model.Customer;
import com.example.model.Transfer;
import com.example.service.customer.ICustomerService;
import com.example.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("customers/transfer")
public class TransferController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITransferService transferService;
    @GetMapping("/{id}")
    public String showTransferPage(@PathVariable("id") String sender_Id, Model model){
        Long senderId = Long.parseLong(sender_Id);
        Optional<Customer> customerOptional = customerService.findById(senderId);
        if(!customerOptional.isPresent()){
            return "404";
        }

        Transfer transfer = new Transfer();
        transfer.setFees(10L);
        transfer.setSender(customerOptional.get());
        List<Customer> recipients = customerService.findAllByIdNot(senderId);
        model.addAttribute("recipients", recipients);
        model.addAttribute("transfer", transfer);
        return "customer/history";
    }
    @PostMapping("/{id}")
    public String transfer(@PathVariable("id") String sender_Id, @Validated @ModelAttribute Transfer transfer, BindingResult bindingResult, Model model){
        long senderId = Long.parseLong(sender_Id);
        Optional<Customer> senderOptional = customerService.findById(senderId);
        Customer sender = senderOptional.get();

        Optional<Customer> recipientOptional = customerService.findById(transfer.getRecipient().getId());
        Customer recipient = recipientOptional.get();
        if(bindingResult.hasFieldErrors()){
            model.addAttribute("errors", true);
            return "customer/transfer";
        }

        if(!(senderOptional.isPresent() || recipientOptional.isPresent())){
            return "404";
        }
        List<Customer> recipients = customerService.findAllByIdNot(senderId);
        model.addAttribute("recipients", recipients);

        BigDecimal senderBalance = sender.getBalance();
        BigDecimal recipientBalance = recipient.getBalance();

        BigDecimal transferAmount = transfer.getTransferAmount();
        Long fees = 10L;

        BigDecimal minAmount = BigDecimal.valueOf(10000);
        BigDecimal maxAmount = BigDecimal.valueOf(1000000000);

        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100L));
        BigDecimal totalAmount = transferAmount.add(feesAmount);

        if(transferAmount.compareTo(minAmount) < 0){
            model.addAttribute("error", "Amount must be more than 10.000 VNĐ");
            return "customer/transfer";
        } else if(transferAmount.compareTo(maxAmount) > 0){
            model.addAttribute("error", "Amount must be less than 1.000.000.000 VNĐ");
            return "customer/transfer";
        } else {
            if(sender.getId() == recipient.getId()){
                model.addAttribute("error", "Invalid recipient account");
                return "customer/transfer";
            }
            if(senderBalance.compareTo(totalAmount) < 0){
                model.addAttribute("error", "The balance is not enough to make this transaction");
                return "customer/transfer";
            }
        }

        BigDecimal sender_newBalance = senderBalance.subtract(totalAmount);
        sender.setBalance(sender_newBalance);

        BigDecimal recipient_newBalance = recipientBalance.add(transferAmount);
        recipient.setBalance(recipient_newBalance);

        transfer.setRecipient(recipient);
        transfer.setSender(sender);
        transfer.setTransferAmount(transferAmount);
        transfer.setTotalAmount(totalAmount);
        transfer.setFees(fees);
        transfer.setFeesAmount(feesAmount);

        customerService.transfer(transfer);

        model.addAttribute("message", "Transfer transaction is successful!!!");
        transfer.setTransferAmount(BigDecimal.ZERO);

        return "customer/transfer";
    }
}
