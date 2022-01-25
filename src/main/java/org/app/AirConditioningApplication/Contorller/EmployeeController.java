package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Employee;
import org.app.AirConditioningApplication.Model.WorkLog;
import org.app.AirConditioningApplication.Service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("Employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        return employeeService.showAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam Long Id) {
        return employeeService.delete(Id);
    }

    @GetMapping("/getByID")
    public ResponseEntity<Object> getById(@RequestParam Long Id) {
        return employeeService.getById(Id);
    }

    //This api will add workLog against employee because employee will exist for worklog
    @PutMapping("/addWorkLog")
    public ResponseEntity<Object> addWorkLog(@RequestParam Long EmployeeId, @RequestBody WorkLog workLog) {
        return employeeService.getPriceByHour(EmployeeId, workLog);
    }

    //This api will only show workLog by email of employee
    @PostMapping("/emailWorkLog")
    public ResponseEntity<Object> workLogByEmail(@RequestParam String email){
        return employeeService.showWorkLog(email);
    }

}
