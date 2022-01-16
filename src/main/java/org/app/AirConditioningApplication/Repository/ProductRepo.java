package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
