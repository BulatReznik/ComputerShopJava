package ru.ulstu.is.sbapp.student.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "customer")
    private List<Orderr> orderrs;

    @Column(nullable = false)
    @NotBlank(message = "First Name can't be null or empty")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last Name can't be null or empty")
    private String lastName;

    @Column(nullable = false)
    @NotBlank(message = "Login can't be null or empty")
    private String login;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String login, List<Orderr> orderrs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.orderrs = orderrs;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) { this.login = login; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer seller = (Customer) o;
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addOrderr(Orderr orderr)
    {
        orderrs.add(orderr);
        if(orderr.getCustomer()!= this){
            orderr.setCustomer(this);
        }
    }
    public List<Orderr> getOrderrs(){return this.orderrs;}

    public void setOrderrs(List<Orderr> orderrs) {this.orderrs = orderrs; }


    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", orderrs=" + orderrs +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
