package org.app.AirConditioningApplication.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;
    private String name;
    //private String customerName;
    private int totalPrice;
    private String budgetStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "budget_id", referencedColumnName = "budgetId")
    List<Product> productList;

    @OneToOne(cascade = CascadeType.ALL)
    Customer customer;

}
