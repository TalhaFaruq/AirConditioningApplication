package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepo extends JpaRepository<Budget,Long> {


}
