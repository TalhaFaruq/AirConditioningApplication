package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepo extends JpaRepository<Services, Long> {
}
