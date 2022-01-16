package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
}
