package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Customer;
import org.app.AirConditioningApplication.Repository.CustomerRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public ResponseEntity<Object> save(Customer customer) {
        try {
            customerRepo.save(customer);
            return ResponseEntity.accepted().body(customer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> showAll() {
        try {
            List<Customer> customerList = customerRepo.findAll();
            if (!customerList.isEmpty())
                return ResponseEntity.ok().body(customerList);
            else
                return ResponseEntity.ok().body("There are no employees");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    public ResponseEntity<Object> getById(Long Id) {
        try {
            Optional<Customer> customer = customerRepo.findById(Id);
            if(customer.isPresent())
                return ResponseEntity.ok().body(customer);
            else return ResponseEntity.ok().body("Invalid Id");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<Customer> customer = customerRepo.findById(Id);
            if(customer.isPresent()){
                customerRepo.delete(customer.get());
                return ResponseEntity.ok().body("Deleted");
            }else return ResponseEntity.ok().body("Invalid Id");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

}
