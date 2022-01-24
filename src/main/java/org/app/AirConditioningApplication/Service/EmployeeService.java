package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Employee;
import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Model.WorkLog;
import org.app.AirConditioningApplication.Repository.EmployeeRepo;
import org.app.AirConditioningApplication.Repository.OrderRepo;
import org.app.AirConditioningApplication.Repository.WorkLogRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final OrderRepo orderRepo;
    private final WorkLogRepo workLogRepo;


    public EmployeeService(EmployeeRepo employeeRepo, OrderRepo orderRepo, WorkLogRepo workLogRepo) {
        this.employeeRepo = employeeRepo;
        this.orderRepo = orderRepo;
        this.workLogRepo = workLogRepo;
    }

    public ResponseEntity<Object> save(Employee employee) {
        try {
            List<WorkLog> workLogList = employee.getWorkLogList();
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
            if (employee.isPresent()) {
                employee.get().setWorkLogList(null);
                employeeRepo.delete(employee.get());
                return ResponseEntity.ok().body("Deleted");
            } else return ResponseEntity.ok().body("Invalid Id");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    // The employee will tell how many hour will the work take. So after order is saved then the id of employee with order id needed to put
    // the working hours in the database and calculate the price
    public ResponseEntity<Object> getPriceByHour(Long eid, WorkLog workLog) {
        try {
            Optional<Employee> emp = employeeRepo.findById(eid);
            if (emp.isPresent()) {
                workLog.setDate(LocalDate.now());
                Optional<Order> order = orderRepo.findById(workLog.getOrder().getOrderId());
                if (order.isPresent()) {
                    workLog.setOrder(order.get());
                }
                emp.get().getWorkLogList().add(workLog);
                employeeRepo.save(emp.get());
                return ResponseEntity.ok().body(emp);
            } else return ResponseEntity.ok().body("The Employee or Order does not exist");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    //Worklog will only be shown by the email.
    public ResponseEntity<Object> showWorkLog(String email){
        Optional<Employee> employee = employeeRepo.findEmployeeByEmail(email);
        if(employee.isPresent()){
            return ResponseEntity.ok().body(employee.get().getWorkLogList());
        }else return ResponseEntity.ok().body("The email is invalid");
    }
}
