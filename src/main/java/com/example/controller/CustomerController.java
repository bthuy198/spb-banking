package com.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/customers", "/"})
public class CustomerController {
    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("customer/list");
    }
    //    private static Sort sort = Sort.by("createdAt").ascending();
//    @Autowired
//    private ICustomerService customerService;
//    @Autowired
//    private ITransferService transferService;
//    @Autowired
//    private IDepositService depositService;
//    @Autowired
//    IWithdrawService withdrawService;
//
//    @GetMapping
//    public String showList(Model model) {
//        List<Customer> customers = customerService.findAll();
//        model.addAttribute("customers", customers);
//        return "customer/list";
//    }
//
//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("customer", new Customer());
//        return "customer/list";
//    }
//
//    @PostMapping("/create")
//    public String createCustomer(@Validated @ModelAttribute Customer customer, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasFieldErrors()) {
//            model.addAttribute("errors", true);
//            model.addAttribute("customer", customer);
//            return "customer/list";
//        }
//        List<Customer> email = customerService.findEmailByEmail(customer.getEmail());
//        if (email.size() != 0) {
//            model.addAttribute("err", "Email has been exist");
//            model.addAttribute("customer", customer);
//            return "customer/list";
//        }
//        customer.setBalance(BigDecimal.ZERO);
//        customerService.save(customer);
//        model.addAttribute("message", "Added new customer successfully!!!");
//        model.addAttribute("customer", new Customer());
//        return "customer/list";
//    }
//
//    @GetMapping("/update/{id}")
//    public String showUpdateForm(@PathVariable long id, Model model) {
//        Optional<Customer> customerOptional = customerService.findById(id);
//        if (!customerOptional.isPresent()) {
//            return "404";
//        }
//        model.addAttribute("customer", customerOptional.get());
//        return "customer/update";
//    }
//
//    @PostMapping("/update/{id}")
//    public String update(@PathVariable long id, @Validated @ModelAttribute Customer customer, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasFieldErrors()) {
//            model.addAttribute("errors", true);
//            model.addAttribute("customer", customer);
//            return "customer/update";
//        }
//        List<Customer> email = customerService.findEmailByEmail(customer.getEmail());
//        if (email.size() != 0) {
//            model.addAttribute("err", "Email has been exist");
//            model.addAttribute("customer", customer);
//            return "customer/create";
//        }
//        Optional<Customer> customerOptional = customerService.findById(id);
//        customer.setBalance(customerOptional.get().getBalance());
//        customer.setId(id);
//        customerService.save(customer);
//        model.addAttribute("message", "Updated customer's information");
//        model.addAttribute("customer", customer);
//        return "customer/update";
//    }
//
//    @GetMapping("/history")
//    public String showHistoryPage(Model model) {
//        List<Transfer> transfers = transferService.findAll();
//        model.addAttribute("transfers", transfers);
//
//        List<Deposit> deposits = depositService.findAll();
//        model.addAttribute("deposits", deposits);
//
//        List<Withdraw> withdraws = withdrawService.findAll();
//        model.addAttribute("withdraws", withdraws);
//
////        List<HistoryView> historyViews = historyViewRepository.findAll();
////        model.addAttribute("historyViews", historyViews);
//        return "customer/history";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String showDeletePage(@PathVariable long id, Model model) {
//        Optional<Customer> optionalCustomer = customerService.findById(id);
//        if (!optionalCustomer.isPresent()) {
//            return "404";
//        }
//        model.addAttribute("customer", optionalCustomer.get());
//        return "customer/suspended";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String remove(@PathVariable long id, Model model) {
//        Optional<Customer> optionalCustomer = customerService.findById(id);
//        if (!optionalCustomer.isPresent()) {
//            return "404";
//        }
//        customerService.deleteById(id);
//        return "customer/list";
//    }
//
//    @GetMapping("/deleted")
//    public String showDeletePage(Model model) {
//        List<CustomerDTO> customerDTOS = customerService.findAllCustomerDTOAndAndDeletedTrue();
//        model.addAttribute("customer", customerDTOS);
//        return "customer/list";
//    }

//    @GetMapping("/search")
//    public String search(@RequestParam("searchKey") String searchKey, Model model){
//        searchKey = "%" + searchKey +"%";
//        List<Customer> result = customerService.findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(searchKey, searchKey, searchKey, searchKey);
//
//        model.addAttribute("customers", result);
//
//        return "customer/list";
//    }
}
