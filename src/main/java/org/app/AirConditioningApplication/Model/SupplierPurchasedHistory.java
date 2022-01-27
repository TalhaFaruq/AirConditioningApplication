package org.app.AirConditioningApplication.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "purchased_history_from_supplier")
public class SupplierPurchasedHistory {
    @Id
    private String supplierOrderId = UUID.randomUUID().toString();
    private double totalPrice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_order_id", referencedColumnName = "supplierOrderId")
    List<SupplierProduct> supplierProducts = new ArrayList<>();
}
