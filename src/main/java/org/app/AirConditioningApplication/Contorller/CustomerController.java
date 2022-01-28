package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Customer;
import org.app.AirConditioningApplication.Service.CustomerService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("Customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return customerService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam Long Id) {
        return customerService.delete(Id);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam Long Id) {
        return customerService.getById(Id);
    }

}
