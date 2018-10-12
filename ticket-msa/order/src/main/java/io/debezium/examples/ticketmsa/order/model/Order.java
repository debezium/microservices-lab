package io.debezium.examples.ticketmsa.order.model;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "MSA_ORDER")
@XmlRootElement
public class Order {

    @Id
    @GeneratedValue
    private int id;

    public Order() {
    }

    public Order(String firstName, String lastName, String email, BigDecimal price) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.price = price;
    }

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private BigDecimal price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("email", email)
                .add("price", price)
                .build();
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", price=" + price + "]";
    }
}
