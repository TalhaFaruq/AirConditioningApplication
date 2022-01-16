package org.app.AirConditioningApplication.Models;

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
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    private String orderNumber;
    private String productSold;
    private int basePrice;
    private int tax;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplierId")
    List<Product> productList;
}
