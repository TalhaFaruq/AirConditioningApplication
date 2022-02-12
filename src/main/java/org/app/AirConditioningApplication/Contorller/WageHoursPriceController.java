package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.WageHoursPrice;
import org.app.AirConditioningApplication.Service.WageHoursPriceService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("WageHoursPrice")
public class WageHoursPriceController {
    private final WageHoursPriceService wageHoursPriceService;

    @Autowired
    public WageHoursPriceController(WageHoursPriceService wageHoursPriceService) {
        this.wageHoursPriceService = wageHoursPriceService;
    }

    @PostMapping("/save")
    public ApiResponse create(@RequestBody WageHoursPrice wageHoursPrice) {
        return wageHoursPriceService.save(wageHoursPrice);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody WageHoursPrice wageHoursPrice) {
        return wageHoursPriceService.save(wageHoursPrice);
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return wageHoursPriceService.showAll();
    }
}
