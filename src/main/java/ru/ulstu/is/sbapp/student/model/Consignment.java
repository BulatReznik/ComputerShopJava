package ru.ulstu.is.sbapp.student.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Consignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name of consignment can't be null or empty")
    @Column()
    private String consignmentName;

    @ManyToMany
    @JoinTable(name = "consignments_orderrs",
            joinColumns =  @JoinColumn(name = "consignment_id"),
            inverseJoinColumns = @JoinColumn(name = "orderrs_id"))
    private List<Orderr> orderrs = new ArrayList<>();

    public Consignment() {
    }

    public Consignment(String consignmentName, List<Orderr> orderrs) {
        this.consignmentName = consignmentName;
        this.orderrs=orderrs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getConsignmentName() {
        return consignmentName;
    }

    public void setConsignmentName(String consignmentName) {
        this.consignmentName = consignmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consignment consignment = (Consignment) o;
        return Objects.equals(id, consignment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setOrderrs (List<Orderr> orderrs)
    {
        this.orderrs = orderrs;
    }

    public void addOrderr(Orderr orderr) {
        if(!this.orderrs.contains(orderr)){
            this.orderrs.add(orderr);
        }
        if (!orderr.getConsignments().contains(this)) {
            orderr.getConsignments().add(this);
        }

    }

    public List<Orderr> getOrderrs(){return this.orderrs;}
    public void deleteOrderr(Orderr orderr){
        if(this.orderrs.contains(orderr)){
            this.orderrs.remove(orderr);
        }
    }
    @Override
    public String toString() {
        return "Consignment{" +
                "id=" + id +
                ", consignmentName='" + consignmentName + '\'' +
                ", orderrs=" + orderrs.stream().map(Orderr::getOrderrName).collect(Collectors.joining("\n"))+
                '}';
    }
}
