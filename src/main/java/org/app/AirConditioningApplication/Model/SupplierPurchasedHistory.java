package org.app.AirConditioningApplication.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "purchased_history_from_supplier")
public class SupplierPurchasedHistory {
    @Id
    private String orderId = UUID.randomUUID().toString();
    private double totalPrice;
    @OneToOne
    private SupplierProduct product;
}
