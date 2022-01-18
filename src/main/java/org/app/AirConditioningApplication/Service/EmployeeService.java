package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Employee;
import org.app.AirConditioningApplication.Repository.EmployeeRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public ResponseEntity<Object> save(Employee employee) {
        try {
            employeeRepo.save(employee);
            return ResponseEntity.accepted().body(employee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> showAll() {
        try {
            List<Employee> employeeList = employeeRepo.findAll();
            if (!employeeList.isEmpty())
                return ResponseEntity.ok().body(employeeList);
            else
                return ResponseEntity.ok().body("There are no employees");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    public ResponseEntity<Object> getById(Long Id) {
        try {
            Optional<Employee> employee = employeeRepo.findById(Id);
            if(employee.isPresent())
                return ResponseEntity.ok().body(employee);
            else return ResponseEntity.ok().body("Invalid Id");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<Employee> employee = employeeRepo.findById(Id);
            if(employee.isPresent()){
                employeeRepo.delete(employee.get());
                return ResponseEntity.ok().body("Deleted");
            }else return ResponseEntity.ok().body("Invalid Id");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
