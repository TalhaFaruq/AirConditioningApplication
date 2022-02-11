package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.WageHoursPrice;
import org.app.AirConditioningApplication.Repository.WageHoursPriceRepo;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WageHoursPriceService {
    private final WageHoursPriceRepo wageHoursPriceRepo;

    @Autowired
    public WageHoursPriceService(WageHoursPriceRepo wageHoursPriceRepo) {
        this.wageHoursPriceRepo = wageHoursPriceRepo;
    }

    public ApiResponse save(WageHoursPrice wageHoursPrice) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            wageHoursPriceRepo.save(wageHoursPrice);
            apiResponse.setMessage("WageHours Successfully added in the database");
            apiResponse.setData(wageHoursPrice);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    public ApiResponse showAll() {
        ApiResponse apiResponse = new ApiResponse();
        try {
            List<WageHoursPrice> wageHoursPrice = wageHoursPriceRepo.findAll();
            if (wageHoursPrice.isEmpty()) {
                apiResponse.setMessage("There are no WageHours Price in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                apiResponse.setMessage("Following is the list of WageHours Price in the database");
                apiResponse.setStatus(HttpStatus.OK.value());
            }
            apiResponse.setData(wageHoursPrice.get(0));
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }
}
