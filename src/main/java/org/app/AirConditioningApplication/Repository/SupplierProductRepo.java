package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierProductRepo extends JpaRepository<SupplierProduct, Long> {
    Optional<SupplierProduct> findAllByNameAndAndCharacteristics(String name, String characteristics);
}
