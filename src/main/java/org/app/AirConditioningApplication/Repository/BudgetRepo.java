package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepo extends JpaRepository<Budget,Long> {
}
