package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.SupplierPurchasedHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierPurchasedHistoryRepository extends JpaRepository<SupplierPurchasedHistory, String> {
}
