package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.student.controller.dto.ConsignmentDto;
import ru.ulstu.is.sbapp.student.model.Consignment;
import ru.ulstu.is.sbapp.student.model.Customer;
import ru.ulstu.is.sbapp.student.model.Orderr;
import ru.ulstu.is.sbapp.student.repository.ConsignmentRepository;
import ru.ulstu.is.sbapp.student.repository.OrderrRepository;
import ru.ulstu.is.sbapp.student.service.exeption.ConsignmentNotFoundException;
import ru.ulstu.is.sbapp.student.service.exeption.OrderrNotFoundException;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ConsignmentService {
    private final ConsignmentRepository consignmentRepository;
    private final ValidatorUtil validatorUtil;
    private final OrderrRepository orderrRepository;
    private final OrderrService orderrService;

    public ConsignmentService(ConsignmentRepository consignmentRepository,
                              ValidatorUtil validatorUtil, OrderrRepository orderrRepository, OrderrService orderrService) {
        this.consignmentRepository = consignmentRepository;
        this.validatorUtil = validatorUtil;
        this.orderrRepository = orderrRepository;
        this.orderrService = orderrService;
    }

    @Transactional
    public Consignment addConsignment(String consignmentName, List<Orderr> orderrs) {
        final Consignment consignment = new Consignment (consignmentName, orderrs);
        validatorUtil.validate(consignment);
        return consignmentRepository.save(consignment);
    }

    @Transactional
    public ConsignmentDto addConsignment(ConsignmentDto consignmentDto) {

        String consignmentName = consignmentDto.getConsignmentName();
        List<Orderr> orderrs = Collections.emptyList();
        Customer customer = null;
        if(consignmentDto.getOrderrs()!=null){
            orderrs = consignmentDto.getOrderrs().stream()
                    .map(orderrId -> orderrRepository.findById(orderrId)
                            .orElseThrow(()-> new OrderrNotFoundException(orderrId))).toList();
        }

        return new ConsignmentDto(addConsignment(consignmentName, orderrs));
    }

    @Transactional(readOnly = true)
    public Consignment findConsignment(Long id) {
        final Optional<Consignment> consignment = consignmentRepository.findById(id);
        return consignment.orElseThrow(() -> new ConsignmentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Consignment> findAllConsignments() {
        return consignmentRepository.findAll();
    }

    @Transactional
    public Consignment updateConsignment(Long id, String consignmentName,List<Orderr> orderrs) {
        final Consignment currentConsignment = findConsignment(id);
        currentConsignment.setConsignmentName(consignmentName);
        for(Orderr orderr: currentConsignment.getOrderrs()){
            orderr.getConsignments().remove(id);
        }
        currentConsignment.getOrderrs().clear();
        for(Orderr orderr: orderrs){
            currentConsignment.addOrderr(orderr);
        }
        validatorUtil.validate(currentConsignment);
        return consignmentRepository.save(currentConsignment);
    }
    @Transactional
    public ConsignmentDto updateConsignment (Long id, ConsignmentDto consignmentDto){
        List<Orderr> orderrs = Collections.emptyList();
        String consignmentName;
        if(consignmentDto.getOrderrs()!=null) {
            consignmentDto.getOrderrs().stream()
                    .map(orderrId -> orderrRepository.findById(orderrId)
                            .orElseThrow(() -> new OrderrNotFoundException(orderrId))).toList();
        }
        if(consignmentDto.getConsignmentName() == null){
            consignmentName = findConsignment(id).getConsignmentName();
        }
        else {
            consignmentName = consignmentDto.getConsignmentName().split(";")[0];
        }
        return new ConsignmentDto(updateConsignment(id, consignmentName, orderrs));
    }

    @Transactional
    public Consignment deleteConsignment(Long id) {
        final Consignment currentConsignment = findConsignment(id);
        consignmentRepository.delete(currentConsignment);
        return currentConsignment;
    }

    @Transactional
    public void addConsignmentOrderr(Long consignmentId,Long orderrId){
        var consignment = findConsignment(consignmentId);
        validatorUtil.validate(consignment);
        var orderr = orderrService.findOrderr(orderrId);
        validatorUtil.validate(orderr);
        consignment.addOrderr(orderr);
        orderrRepository.save(orderr);
    }
    /*
    @Transactional
    public List<ConsignmentDto> getConsignmentForCustomer(Long id) {
        return consignmentRepository.getConsignmentForCustomer(id).stream()
                .map(ConsignmentDto::new)
                .collect(Collectors.toList());
    }
    */
}
