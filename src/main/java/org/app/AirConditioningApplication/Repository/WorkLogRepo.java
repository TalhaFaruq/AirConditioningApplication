package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLogRepo extends JpaRepository<WorkLog, Long> {
}
