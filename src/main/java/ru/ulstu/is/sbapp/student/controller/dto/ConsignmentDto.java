package ru.ulstu.is.sbapp.student.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.student.model.Consignment;
import ru.ulstu.is.sbapp.student.model.Orderr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConsignmentDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String consignmentName;
    private List<Long> orderrs = new ArrayList<>();
    private List<String> orderrsName;

    public ConsignmentDto() {
    }

    public ConsignmentDto(Consignment consignment) {
        this.id = consignment.getId();
        this.consignmentName = consignment.getConsignmentName();
        List<Orderr> orderrList = consignment.getOrderrs();
        if(orderrList != null) {
            for (Orderr orderr : orderrList) {
                this.orderrs.add(orderr.getId());
            }
        }else {
            this.orderrs = Collections.emptyList();
        }

        this.orderrsName = new ArrayList<>();
        for (Orderr orderr : consignment.getOrderrs()) {
            this.orderrsName.add(String.format("%s от '%s'", orderr.getOrderrName(), orderr.getOrderrDate()));
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getConsignmentName() {
        return consignmentName;
    }
    public List<Long> getOrderrs() {
        return orderrs;
    }
    public void setConsignmentName(String consignmentName) {this.consignmentName = consignmentName; }
    public void setOrderrs(List<Long> orderrs) {this.orderrs = orderrs; }
    public void setOrderrsName(List<String> orderrsName) { this.orderrsName = orderrsName; }
    public List<String> getOrderrsName() { return orderrsName;}
    public String prettyOrderrs(){
        return String.join("; ", orderrsName);
    }

}
