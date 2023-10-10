package ru.ulstu.is.sbapp.student.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.WebConfiguration;
import ru.ulstu.is.sbapp.student.controller.dto.ConsignmentDto;
import ru.ulstu.is.sbapp.student.service.ConsignmentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/consignment")

public class ConsignmentController {

    private final ConsignmentService consignmentService;

    public ConsignmentController(ConsignmentService consignmentService) {this.consignmentService = consignmentService; }

    @GetMapping("/{id}")
    public ConsignmentDto getConsignment(@PathVariable Long id) {
        return new ConsignmentDto(consignmentService.findConsignment(id));
    }

    @GetMapping("/")
    public List<ConsignmentDto> getConsignments() {
        return consignmentService.findAllConsignments().stream()
                .map(ConsignmentDto::new)
                .toList();
    }

    @PostMapping("/")
    public ConsignmentDto createConsignment(@RequestBody @Valid ConsignmentDto consignmentDto){
        return consignmentService.addConsignment(consignmentDto);
    }

    @PutMapping("/{id}")
    public ConsignmentDto updateConsignment(@PathVariable Long id, @RequestBody @Valid ConsignmentDto consignmentDto) {
        return consignmentService.updateConsignment(id, consignmentDto);
    }

    @DeleteMapping("/{id}")
    public ConsignmentDto deleteConsignment(@PathVariable Long id) {
        return new ConsignmentDto(consignmentService.deleteConsignment(id));
    }
}