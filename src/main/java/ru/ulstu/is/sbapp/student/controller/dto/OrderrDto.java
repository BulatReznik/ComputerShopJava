package ru.ulstu.is.sbapp.student.controller.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.student.model.Consignment;
import ru.ulstu.is.sbapp.student.model.Orderr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderrDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String orderrName;
    private String orderrDate;
    private List<Long> consignments = new ArrayList<>();
    private CustomerDto customer;
    private List<String> consignmentsName;

    public OrderrDto() {

    }

    public OrderrDto(Orderr orderr) {
        this.id = orderr.getId();
        this.orderrName = orderr.getOrderrName();
        this.orderrDate = orderr.getOrderrDate();
        this.consignments = new ArrayList<>();
        List<Consignment> consignmentList = orderr.getConsignments();
        if(consignmentList != null) {
            for (Consignment consignment : consignmentList) {
                this.consignments.add(consignment.getId());
            }
        }else {
            this.consignments = Collections.emptyList();
        }
        this.consignmentsName = new ArrayList<>();
        for (Consignment consignment : orderr.getConsignments()) {
            this.consignmentsName.add(String.format("%s", consignment.getConsignmentName()));
        }
        if(orderr.getCustomer() != null){
            this.customer = new CustomerDto(orderr.getCustomer());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderrName() {
        return orderrName;
    }

    public void setOrderrName(String orderrName) {this.orderrName = orderrName;
    }
    public String getOrderrDate() {
        return orderrDate;
    }

    public void setOrderrDate(String orderrDate) {this.orderrDate = orderrDate; }

    public List<Long> getConsignments() {
        return consignments;
    }

    public void setConsignments(List<Long> consignments) {this.consignments = consignments; }

    public CustomerDto getCustomer(){return customer;}

    public void setCustomer(CustomerDto customer) { this.customer = customer; }

    public List<String> getConsignmentsName() {
        return consignmentsName;
    }

    public void setConsignmentsName(List<String> consignmentsName) {
        this.consignmentsName = consignmentsName;
    }

    public String prettyConsignments(){return String.join("; ",consignmentsName); }

}