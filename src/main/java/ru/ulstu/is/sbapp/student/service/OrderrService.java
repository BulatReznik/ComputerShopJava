package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.student.controller.dto.OrderrDto;
import ru.ulstu.is.sbapp.student.model.Consignment;
import ru.ulstu.is.sbapp.student.model.Customer;
import ru.ulstu.is.sbapp.student.model.Orderr;
import ru.ulstu.is.sbapp.student.repository.ConsignmentRepository;
import ru.ulstu.is.sbapp.student.repository.CustomerRepository;
import ru.ulstu.is.sbapp.student.repository.OrderrRepository;
import ru.ulstu.is.sbapp.student.service.exeption.ConsignmentNotFoundException;
import ru.ulstu.is.sbapp.student.service.exeption.CustomerNotFoundException;
import ru.ulstu.is.sbapp.student.service.exeption.OrderrNotFoundException;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderrService {
    private final OrderrRepository orderrRepository;
    private final ValidatorUtil validatorUtil;
    private final ConsignmentRepository consignmentRepository;
    private final CustomerRepository customerRepository;

    public OrderrService (OrderrRepository orderrRepository, ValidatorUtil validatorUtil,
                          ConsignmentRepository consignmentRepository, CustomerRepository customerRepository){
        this.orderrRepository = orderrRepository;
        this.validatorUtil = validatorUtil;
        this.consignmentRepository = consignmentRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Orderr addOrderr(String orderName, String orderDate, List<Consignment> consignments, Customer customer) {
        final Orderr orderr = new Orderr(orderName,orderDate,consignments,customer);
        validatorUtil.validate(orderr);
        return orderrRepository.save(orderr);
    }

    @Transactional
    public OrderrDto addOrderr(OrderrDto orderrDto) {
        String orderrName;
        String orderrDate;
        List<Consignment> consignments = Collections.emptyList();
        if(orderrDto.getConsignments()!=null){
            consignments = orderrDto.getConsignments().stream()
                    .map(consignmentId -> consignmentRepository.findById(consignmentId)
                            .orElseThrow(()-> new ConsignmentNotFoundException(consignmentId))).toList();
        }
        Customer customer = null;
        if(orderrDto.getCustomer()!=null){
            customer = customerRepository.findById(orderrDto.getCustomer().getId())
                    .orElseThrow(()-> new CustomerNotFoundException(orderrDto.getCustomer().getId()));
        }
        if(orderrDto.getOrderrName() == null){
            orderrName = findOrderr(orderrDto.getId()).getOrderrName();
        }
        else {
            orderrName= orderrDto.getOrderrName().split(";")[0];
        } if(orderrDto.getOrderrName() == null){
            orderrDate = findOrderr(orderrDto.getId()).getOrderrDate();
        }
        else {
            orderrDate = orderrDto.getOrderrDate().split(";")[0];
        }
        return new OrderrDto(addOrderr(orderrName,orderrDate,consignments,customer));
    }

    @Transactional(readOnly = true)
    public Orderr findOrderr(Long id) {
        final Optional<Orderr> orderr = orderrRepository.findById(id);
        return orderr.orElseThrow(() -> new OrderrNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Orderr> findAllOrderrs() {
        return orderrRepository.findAll();
    }

    @Transactional
    public Orderr updateOrderr(Long id, String orderName, String orderDate,
                               List<Consignment> consignments, Customer customer) {

        final Orderr currentOrderr = findOrderr(id);
        currentOrderr.setOrderrName(orderName);
        currentOrderr.setOrderrDate(orderDate);
        currentOrderr.setCustomer(customer);
        for (Consignment consignment: consignments) {
            if (consignment.getConsignmentName() != null){
                currentOrderr.addConsignment(consignment);
            }
        }

        validatorUtil.validate(currentOrderr);
        return orderrRepository.save(currentOrderr);
    }

    @Transactional
    public OrderrDto updateOrderr (Long id, OrderrDto orderrDto){
        String orderrName;
        String orderrDate;
        List<Consignment> consignments = Collections.emptyList();
        if(orderrDto.getConsignments()!=null) {
            consignments = orderrDto.getConsignments().stream()
                    .map(consignmentId -> consignmentRepository.findById(consignmentId).orElseThrow(() -> new ConsignmentNotFoundException(consignmentId))).toList();
        }

        Customer customer = null;
        if(orderrDto.getCustomer()!=null){
            customer = customerRepository.findById(orderrDto.getCustomer().getId())
                    .orElseThrow(()-> new CustomerNotFoundException(orderrDto.getCustomer().getId()));
        }

        if(orderrDto.getOrderrName() == null){
            orderrName = findOrderr(id).getOrderrName();
        }
        else {
            orderrName = orderrDto.getOrderrName().split(";")[0];
        }

        if(orderrDto.getOrderrDate()== null){
            orderrDate = findOrderr(id).getOrderrDate();
        }
        else {
            orderrDate = orderrDto.getOrderrDate().split(";")[0];
        }

        return new OrderrDto(updateOrderr(id, orderrName, orderrDate, consignments, customer));
    }

    @Transactional
    public Orderr deleteOrderr(Long id) {
        final Orderr currentOrderr = findOrderr(id);
        List<Consignment> consignments = currentOrderr.getConsignments();
        for (Consignment cs: consignments){
            cs.deleteOrderr(currentOrderr);
        }
        orderrRepository.delete(currentOrderr);
        return currentOrderr;
    }
}
