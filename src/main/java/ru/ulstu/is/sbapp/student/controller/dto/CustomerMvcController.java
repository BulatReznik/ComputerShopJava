package ru.ulstu.is.sbapp.student.controller.dto;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.student.model.Orderr;
import ru.ulstu.is.sbapp.student.repository.OrderrRepository;
import ru.ulstu.is.sbapp.student.service.CustomerService;
import ru.ulstu.is.sbapp.student.service.exeption.OrderrNotFoundException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerMvcController {
    private final CustomerService customerService;
    private final OrderrRepository orderrRepository;

    public CustomerMvcController(CustomerService customerService, OrderrRepository orderrService){
        this.customerService = customerService;
        this.orderrRepository = orderrService;
    }

    @GetMapping
    public String getCustomers(Model model) {
        model.addAttribute("customers",
                customerService.findAllCustomers().stream()
                        .map(CustomerDto::new)
                        .toList());
        return "customer";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editCustomer(@PathVariable(required = false) Long id,
                            Model model) {
        model.addAttribute("orderrs", orderrRepository.findAll().stream()
                .map(OrderrDto::new)
                .toList());
        if (id == null || id <= 0) {
            model.addAttribute("customerDto", new CustomerDto());
        } else {
            model.addAttribute("customerId", id);
            model.addAttribute("customerDto", new CustomerDto(customerService.findCustomer(id)));
        }
        return "customer-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveCustomer(@PathVariable(required = false) Long id,
                               @ModelAttribute @Valid CustomerDto customerDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "customer-edit";
        }
        List<Orderr> orderrs = Collections.emptyList();
        if(customerDto.getOrderrs()!=null){
            orderrs = customerDto.getOrderrs().stream()
                    .map(orderrId -> orderrRepository.findById(orderrId)
                            .orElseThrow(()-> new OrderrNotFoundException(orderrId))).toList();
        }
        if (id == null || id <= 0) {
            customerService.addCustomer(customerDto.getFirstName(), customerDto.getLastName(), customerDto.getLogin(), orderrs);
        } else {
            customerService.updateCustomer(id, customerDto.getFirstName(), customerDto.getLastName(), customerDto.getLogin(), orderrs);
        }
        return "redirect:/customer";
    }
    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer";
    }
}
