package com.example.api;

import com.example.exception.DataInputException;
import com.example.exception.FieldExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.*;
import com.example.model.dto.*;
import com.example.service.customer.ICustomerService;
import com.example.service.deposit.IDepositService;
import com.example.service.transfer.ITransferService;
import com.example.service.withdraw.IWithdrawService;
import com.example.springajaxbanking.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITransferService transferService;
    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

        List<CustomerDTO> customerDTOS = customerService.findAllByDeletedIsFalse();

        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {

//        if(bindingResult.hasFieldErrors()){
//            return appUtils.mapErrorToResponse(bindingResult);
//        }

        Boolean existEmail = customerService.existsByEmailEquals(customerDTO.getEmail());

        if (existEmail) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        customerDTO.setId(null);
        customerDTO.setBalance(BigDecimal.ZERO);
        customerDTO.getLocationRegion().setId(null);

        Customer customer = customerDTO.toCustomer();
        customerService.save(customer);
        customerDTO = customer.toCustomerDTO();

        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable long id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);
        if (optionalCustomer.isPresent()) {
            return new ResponseEntity<>(optionalCustomer.get().toCustomerDTO(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerService.findById(id);

        if (!optionalCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerDTO.setId(id);
        customerDTO.setBalance(optionalCustomer.get().getBalance());

        customerService.save(customerDTO.toCustomer());

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> remove(@PathVariable long id) {
        Customer customer = customerService.findById(id).get();
        customerService.delete(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/deposit/{id}")
    public ResponseEntity<?> deposit(@PathVariable long id, @Validated @RequestBody DepositDTO depositDTO, BindingResult bindingResult) {
        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Not found this customer");
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Customer customer = customerOptional.get();

        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(depositDTO.getTransactionAmount()));

        Deposit deposit = new Deposit();
        deposit.setCustomer(customer);
        deposit.setTransactionAmount(transactionAmount);

        customerService.deposit(deposit);

        customer.setBalance(customerService.findById(id).get().getBalance());

        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.OK);
    }

    @PostMapping("/withdraw/{id}")
    public ResponseEntity<?> withdraw(@PathVariable long id, @Validated @RequestBody WithdrawDTO withdrawDTO, BindingResult bindingResult) {
        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Not found this customer");
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Customer customer = customerOptional.get();

        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(withdrawDTO.getTransactionAmount()));
        BigDecimal currentBalance = customerOptional.get().getBalance();

        if (transactionAmount.compareTo(currentBalance) == 1) {
            throw new DataInputException("Your balance not enough to withdraw");
        }
//        BigDecimal newBalance = currentBalance.add(transactionAmount);

        Withdraw withdraw = new Withdraw();

        withdraw.setCustomer(customer);
        withdraw.setTransactionAmount(transactionAmount);
        customerService.withdraw(withdraw);

        customer.setBalance(customerService.findById(id).get().getBalance());

//        customer.setBalance(newBalance);
//        withdraw.setCustomer(customer);
//        customerService.withdraw(withdraw);


        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.OK);
    }

    @PostMapping("/transfer/{id}")
    public ResponseEntity<?> transfer(@PathVariable long id, @Validated @RequestBody TransferDTO transferDTO, BindingResult bindingResult) {
        Optional<Customer> optionalSender = customerService.findById(id);
        long recipientId = Long.parseLong(transferDTO.getRecipientId());
        Optional<Customer> optionalRecipient = customerService.findById(recipientId);

        if (id == recipientId) {
            throw new DataInputException("Invalid Recipient");
        }

        if (!(optionalSender.isPresent()) || !(optionalRecipient.isPresent())) {
            throw new ResourceNotFoundException("Not found Sender or Recipient");
        }

        Customer sender = optionalSender.get();
        Customer recipient = optionalRecipient.get();


        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        BigDecimal senderBalance = sender.getBalance();

        BigDecimal transferAmount = BigDecimal.valueOf(Long.parseLong(transferDTO.getTransferAmount()));
        Long fees = 10L;


        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100L));
        BigDecimal totalAmount = transferAmount.add(feesAmount);

        if (senderBalance.compareTo(totalAmount) < 0) {
            throw new DataInputException("Your balance not enough to make this transaction");
        }

        Transfer transfer = new Transfer();
        transfer.setTransferAmount(transferAmount);
        transfer.setFeesAmount(feesAmount);
        transfer.setTotalAmount(totalAmount);
        transfer.setSender(sender);
        transfer.setRecipient(recipient);
        transfer = customerService.transfer(transfer);

        return new ResponseEntity<>(transfer.transferDTO(), HttpStatus.OK);
    }


    @GetMapping("/transfer")
    public ResponseEntity<?> showTransferHistory(){
        List<TransferViewDTO> transferViewDTOList = transferService.findAllTransferViewDTO();
        return new ResponseEntity<>(transferViewDTOList, HttpStatus.OK);
    }
    @GetMapping("/deposit")
    public ResponseEntity<?> showDepositHistory(){
        List<DepositViewDTO> depositViewDTOS = depositService.findAllDepositViewDTO();
        return new ResponseEntity<>(depositViewDTOS, HttpStatus.OK);
    }

    @GetMapping("/withdraw")
    public ResponseEntity<?> showWithdrawHistory(){
        List<WithdrawViewDTO> withdrawViewDTOS = withdrawService.findAllWithdrawViewDTO();
        return new ResponseEntity<>(withdrawViewDTOS, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<?> getAllCustomerDeleted(){
        List<CustomerDTO> customerDTOS = customerService.findAllCustomerDTOAndAndDeletedTrue();
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }
}
