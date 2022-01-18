package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
