package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
}
