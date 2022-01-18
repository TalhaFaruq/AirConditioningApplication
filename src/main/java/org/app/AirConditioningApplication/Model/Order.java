package org.app.AirConditioningApplication.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String type;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    List<Product> productList;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Customer customer;
}
