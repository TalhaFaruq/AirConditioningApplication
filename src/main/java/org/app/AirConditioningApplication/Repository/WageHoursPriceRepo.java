package org.app.AirConditioningApplication.Repository;

import org.app.AirConditioningApplication.Model.WageHoursPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WageHoursPriceRepo extends JpaRepository<WageHoursPrice, Integer> {
}
