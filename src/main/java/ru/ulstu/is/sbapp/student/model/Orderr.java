package ru.ulstu.is.sbapp.student.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Orderr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Order Name can't be null or empty")
    private String orderrName;

    @Column(nullable = false)
    @NotBlank(message = "Order Date can't be null or empty")
    private String orderrDate;

    @ManyToMany(mappedBy = "orderrs")
    private List<Consignment> consignments = new ArrayList<>();

    @ManyToOne
    private Customer customer;


    public Orderr() {
    }

    public Orderr(String orderrName, String orderrDate, List<Consignment> consignments, Customer customer) {
        this.orderrName = orderrName;
        this.orderrDate = orderrDate;
        this.customer = customer;
        this.consignments = consignments;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) { this.id = id; }

    public String getOrderrName() {
        return orderrName;
    }
    public void setOrderrName(String orderrName) {
        this.orderrName = orderrName;
    }

    public String getOrderrDate() {
        return orderrDate;
    }
    public void setOrderrDate(String orderrDate) {
        this.orderrDate = orderrDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orderr orderr = (Orderr) o;
        return Objects.equals(id, orderr.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Orderr{" +
                "id=" + id +
                ", orderrName='" + orderrName + '\'' +
                ", orderDate='" + orderrDate + '\'' +
                ", consignments='" + consignments.stream().map(Consignment::getConsignmentName).collect(Collectors.joining("\n")) + '\''+
                '}';
    }

    public void setConsignments (List<Consignment> consignments) {this.consignments = consignments;}
    public void addConsignment(Consignment consignment) {
        if (!this.consignments.contains(consignment)) {
            this.consignments.add(consignment);
        }
        if (consignment.getOrderrs().contains(this)) {
            consignment.getOrderrs().add(this);
        }
    }
    public List<Consignment> getConsignments(){return this.consignments;}
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if(customer!=null){
            this.customer = customer;
            if(!customer.getOrderrs().contains(this)){
                customer.getOrderrs().add(this);
            }
        }
    }
}
