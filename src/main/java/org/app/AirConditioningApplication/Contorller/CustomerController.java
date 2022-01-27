package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Customer;
import org.app.AirConditioningApplication.Service.CustomerService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> list() {
        return customerService.showAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam Long Id) {
        return customerService.delete(Id);
    }

    @GetMapping("/getByID")
    public ResponseEntity<Object> getById(@RequestParam Long Id) {
        return customerService.getById(Id);
    }

}
