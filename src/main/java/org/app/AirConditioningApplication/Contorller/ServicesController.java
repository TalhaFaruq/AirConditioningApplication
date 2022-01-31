package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Customer;
import org.app.AirConditioningApplication.Model.Services;
import org.app.AirConditioningApplication.Service.ServicesService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServicesController {
    private final ServicesService servicesService;

    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return servicesService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody Services services) {
        return servicesService.save(services);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody Services services) {
        return servicesService.save(services);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam Long Id) {
        return servicesService.delete(Id);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam Long Id) {
        return servicesService.getById(Id);
    }
}
