package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogRepo extends JpaRepository<WorkLog,Long> {
}
