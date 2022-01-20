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
public class Budget {   //This is Quotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;
    private int totalPrice;
    private String budgetStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "budget_id", referencedColumnName = "budgetId")
    List<Product> productList;

    @OneToOne(cascade = CascadeType.ALL)
    Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", referencedColumnName = "budgetId")
    List<Services> service;

}
