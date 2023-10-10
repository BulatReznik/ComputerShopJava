package ru.ulstu.is.sbapp.student.controller.dto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.student.model.Consignment;
import ru.ulstu.is.sbapp.student.model.Orderr;
import ru.ulstu.is.sbapp.student.repository.OrderrRepository;
import ru.ulstu.is.sbapp.student.service.ConsignmentService;
import ru.ulstu.is.sbapp.student.service.exeption.OrderrNotFoundException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/consignment")
public class ConsignmentMvcController {
    private final ConsignmentService consignmentService;
    private final OrderrRepository orderrRepository;

    public ConsignmentMvcController(ConsignmentService consignmentService, OrderrRepository orderrRepository){
        this.consignmentService=consignmentService;
        this.orderrRepository=orderrRepository;
    }

    @GetMapping
    public String getConsignments(Model model) {
        model.addAttribute("consignments",
                consignmentService.findAllConsignments().stream()
                        .map(ConsignmentDto::new)
                        .toList());
        return "consignment";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editConsignment(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("orderrs", orderrRepository.findAll().stream()
                .map(OrderrDto::new)
                .toList());
        if (id == null || id <= 0) {
            model.addAttribute("consignmentDto", new ConsignmentDto());
        } else {
            model.addAttribute("consignmentId", id);
            model.addAttribute("consignmentDto", new ConsignmentDto(consignmentService.findConsignment(id)));
        }
        return "consignment-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveConsignment(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid ConsignmentDto consignmentDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "consignment-edit";
        }

        List<Orderr> orderrs = Collections.emptyList();
        if(consignmentDto.getOrderrs()!=null){
            orderrs = consignmentDto.getOrderrs().stream()
                    .map(orderrId -> orderrRepository.findById(orderrId)
                            .orElseThrow(()-> new OrderrNotFoundException(orderrId))).toList();
        }

        if (id == null || id <= 0) {
            consignmentService.addConsignment(consignmentDto.getConsignmentName(), orderrs);
        } else {
            consignmentService.updateConsignment(id, consignmentDto.getConsignmentName(), orderrs);
        }
        return "redirect:/consignment";
    }

    @PostMapping("/delete/{id}")
    public String deleteConsignment(@PathVariable Long id) {
        consignmentService.deleteConsignment(id);
        return "redirect:/consignment";
    }
}
