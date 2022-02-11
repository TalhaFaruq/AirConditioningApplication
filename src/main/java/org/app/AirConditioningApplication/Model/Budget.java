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
public class Budget {   //This is Quotation
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", referencedColumnName = "budgetId")
    List<Product> productList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    Customer customer;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", referencedColumnName = "budgetId")
    List<Services> service = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;
    private double totalPrice;
    private String budgetStatus;
    private String budgetName;
    private Integer assistantHours;
    private Integer officerHours;

}
