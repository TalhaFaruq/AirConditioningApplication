package org.app.AirConditioningApplication.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    private String orderNumber = UUID.randomUUID().toString();
    private int basePrice;
    private double tax;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplierId")
    List<Product> productSold;
}
