package ru.ulstu.is.sbapp.student.controller.dto;

import ru.ulstu.is.sbapp.student.model.Consignment;
import ru.ulstu.is.sbapp.student.model.Customer;
import ru.ulstu.is.sbapp.student.model.Orderr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerDto {
    private  Long id;
    private  String firstName;
    private  String lastName;
    private  String login;
    private List<Long> orderrs;
    private List<String> orderrsName;

    public CustomerDto(){

    }

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.login = customer.getLogin();
        this.orderrs = new ArrayList<>();
        List<Orderr> orderrList = customer.getOrderrs();
        if(orderrList != null) {
            for (Orderr orderr : orderrList) {
                this.orderrs.add(orderr.getId());
            }
        }else {
            this.orderrs = Collections.emptyList();
        }
        this.orderrsName = new ArrayList<>();
        for (Orderr orderr : customer.getOrderrs()) {
            this.orderrsName.add(String.format("%s от '%s'", orderr.getOrderrName(), orderr.getOrderrDate()));
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {this.firstName = firstName; }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName) {this.lastName = lastName; }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {this.login = login; }
    public List<Long> getOrderrs() {
        return orderrs;
    }
    public void setOrderrs(List<Long> orderrs) {this.orderrs = orderrs; }
    public String prettyOrderrs(){
        return String.join("; ", orderrsName);
    }
    public List<String> getOrderrsName() {return orderrsName;
    }

    public void setOrderrsName(List<String> orderrsName) {
        this.orderrsName = orderrsName;
    }
}
