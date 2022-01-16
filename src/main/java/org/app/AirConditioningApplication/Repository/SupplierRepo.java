package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<Supplier,Long> {
}
