package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.student.controller.dto.CustomerDto;
import ru.ulstu.is.sbapp.student.model.Consignment;
import ru.ulstu.is.sbapp.student.model.Customer;
import ru.ulstu.is.sbapp.student.model.Orderr;
import ru.ulstu.is.sbapp.student.repository.CustomerRepository;
import ru.ulstu.is.sbapp.student.repository.OrderrRepository;
import ru.ulstu.is.sbapp.student.service.exeption.CustomerNotFoundException;
import ru.ulstu.is.sbapp.student.service.exeption.OrderrNotFoundException;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private  final CustomerRepository customerRepository;
    private final ValidatorUtil validatorUtil;
    private  final OrderrRepository orderrRepository;
    private final OrderrService orderrService;

    public CustomerService(CustomerRepository customerRepository,
                           ValidatorUtil validatorUtil, OrderrRepository orderrRepository, OrderrService orderrService){
        this.customerRepository = customerRepository;
        this.validatorUtil = validatorUtil;
        this.orderrRepository = orderrRepository;
        this.orderrService = orderrService;
    }

    @Transactional
    public Customer addCustomer(String firstName, String lastName, String login, List<Orderr> orderrs) {
        final Customer customer = new Customer(firstName, lastName, login,  orderrs);
        validatorUtil.validate(customer);
        return customerRepository.save(customer);
    }

    @Transactional
    public CustomerDto addCustomer(CustomerDto customerDto) {
        String firstName;
        String lastName;
        String login;
        List<Orderr> orderrs = Collections.emptyList();
        if (customerDto.getOrderrs() != null) {
            orderrs = customerDto.getOrderrs().stream()
                    .map(orderId -> orderrRepository.findById(orderId)
                            .orElseThrow(() -> new OrderrNotFoundException(orderId))).toList();
        }

        if (customerDto.getFirstName() == null) {
            firstName = findCustomer(customerDto.getId()).getFirstName();
        } else {
            firstName = customerDto.getFirstName().split(";")[0];
        }
        if (customerDto.getLastName() == null) {
            lastName = findCustomer(customerDto.getId()).getLastName();
        } else {
            lastName = customerDto.getLastName().split(";")[0];
        }
        if (customerDto.getLogin() == null) {
            login = findCustomer(customerDto.getId()).getLogin();
        } else {
            login = customerDto.getLogin().split(";")[0];
        }
        return new CustomerDto(addCustomer(firstName, lastName, login, orderrs));
    }

    @Transactional(readOnly = true)
    public Customer findCustomer(Long id) {
        final Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer updateCustomer(Long id, String firstName, String lastName, String login, List<Orderr> orderrs) {
        final Customer currentCustomer = findCustomer(id);
        currentCustomer.setFirstName(firstName);
        currentCustomer.setLastName(lastName);
        currentCustomer.setLogin(login);

        for (Orderr orderr: orderrs) {
            if (orderr.getOrderrName() != null){
                currentCustomer.addOrderr(orderr);
            }
        }
        validatorUtil.validate(currentCustomer);
        return customerRepository.save(currentCustomer);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        String firstName;
        String lastName;
        String login;
        List <Orderr> orderrs = Collections.emptyList();

        if(customerDto.getOrderrs()!=null) {
            orderrs = customerDto.getOrderrs().stream()
                    .map(orderrId -> orderrRepository.findById(orderrId)
                            .orElseThrow(() -> new OrderrNotFoundException(orderrId))).toList();
        }
        if(customerDto.getFirstName() == null){
            firstName = findCustomer(customerDto.getId()).getFirstName();
        }
        else {
            firstName = customerDto.getFirstName().split(";")[0];
        }
        if(customerDto.getLastName() == null){
            lastName = findCustomer(customerDto.getId()).getLastName();
        }
        else {
            lastName = customerDto.getLastName().split(";")[0];
        }
        if(customerDto.getLogin() == null){
            login = findCustomer(customerDto.getId()).getLogin();
        }
        else {
            login = customerDto.getLogin().split(";")[0];
        }
        return new CustomerDto(updateCustomer(id, firstName, lastName, login, orderrs));
    }

    @Transactional
    public Customer deleteCustomer(Long id) {
        final Customer currentCustomer = findCustomer(id);
        List<Orderr> orderrs = currentCustomer.getOrderrs();
        for (Orderr or: orderrs){
            orderrService.deleteOrderr(or.getId());
        }
        customerRepository.delete(currentCustomer);
        return currentCustomer;
    }
}
