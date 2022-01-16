package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
