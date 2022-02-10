package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkLogRepo extends JpaRepository<WorkLog, Long> {
    Optional<WorkLog> findWorkLogByOrder_OrderId(Long orderId);
    List<WorkLog> findAllByOrder_OrderId(Long orderId);
}
