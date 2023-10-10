package ru.ulstu.is.sbapp.student.controller.dto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.student.model.Consignment;
import ru.ulstu.is.sbapp.student.model.Customer;
import ru.ulstu.is.sbapp.student.repository.ConsignmentRepository;
import ru.ulstu.is.sbapp.student.repository.CustomerRepository;
import ru.ulstu.is.sbapp.student.service.OrderrService;
import ru.ulstu.is.sbapp.student.service.exeption.ConsignmentNotFoundException;
import ru.ulstu.is.sbapp.student.service.exeption.CustomerNotFoundException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/orderr")
public class OrderrMvcController {
    private final OrderrService orderrService;
    private final CustomerRepository customerRepository;
    private final ConsignmentRepository consignmentRepository;

    public OrderrMvcController(OrderrService orderrService, ConsignmentRepository consignmentRepository, CustomerRepository customerRepository){
        this.consignmentRepository = consignmentRepository;
        this.customerRepository = customerRepository;
        this.orderrService = orderrService;
    }

    @GetMapping
    public String getOrderrs(Model model){
        model.addAttribute("orderrs",
                orderrService.findAllOrderrs().stream()
                        .map(OrderrDto::new)
                        .toList());
        return "orderr";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editOrderr(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("consignments", consignmentRepository.findAll().stream()
                .map(ConsignmentDto::new)
                .toList());
        model.addAttribute("customers", customerRepository.findAll().stream()
                .map(CustomerDto::new)
                .toList());
        if (id == null || id <= 0) {
            model.addAttribute("orderrDto", new OrderrDto());
        } else {
            model.addAttribute("orderrId", id);
            model.addAttribute("orderrDto", new OrderrDto(orderrService.findOrderr(id)));
        }
        return "orderr-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveOrderr(@PathVariable(required = false) Long id,
                             @ModelAttribute @Valid OrderrDto orderrDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "orderr-edit";
        }

        List<Consignment> consignments = Collections.emptyList();

        Customer customer = null;
        if(orderrDto.getConsignments()!=null){
            consignments = orderrDto.getConsignments().stream()
                    .map(consignmentId -> consignmentRepository.findById(consignmentId)
                            .orElseThrow(()-> new ConsignmentNotFoundException(consignmentId))).toList();
        }
        if(orderrDto.getCustomer()!=null){
            customer = customerRepository.findById(orderrDto.getCustomer().getId())
                    .orElseThrow(()-> new CustomerNotFoundException(orderrDto.getCustomer().getId()));
        }

        if (id == null || id <= 0) {
            orderrService.addOrderr(orderrDto.getOrderrName(), orderrDto.getOrderrDate(),
                    consignments, customer);
        } else {
            orderrService.updateOrderr(id, orderrDto.getOrderrName(), orderrDto.getOrderrDate(),
                    consignments, customer);
        }
        return "redirect:/orderr";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrderr(@PathVariable Long id) {
        orderrService.deleteOrderr(id);
        return "redirect:/orderr";
    }
}
