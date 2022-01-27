package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Employee;
import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Model.WorkLog;
import org.app.AirConditioningApplication.Repository.EmployeeRepo;
import org.app.AirConditioningApplication.Repository.OrderRepo;
import org.app.AirConditioningApplication.Repository.WorkLogRepo;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
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

    public ApiResponse save(Employee employee) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            employeeRepo.save(employee);
            apiResponse.setMessage("Successful");
            apiResponse.setData(employee);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse showAll() {
        ApiResponse apiResponse = new ApiResponse();

        try {
            List<Employee> employeeList = employeeRepo.findAll();

            if (employeeList.isEmpty()) {
                apiResponse.setMessage("There is no employee in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());

            } else {
                apiResponse.setMessage("Successful");
                apiResponse.setData(employeeList);
                apiResponse.setStatus(HttpStatus.OK.value());
            }

            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse getById(Long Id) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            Optional<Employee> employee = employeeRepo.findById(Id);

            if (employee.isPresent()) {
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successful");
                apiResponse.setData(employee);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no employee in the database");
            }
            return apiResponse;

        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse delete(Long Id) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            Optional<Employee> employee = employeeRepo.findById(Id);
            if (employee.isPresent()) {
                employee.get().setWorkLogList(null);
                employeeRepo.delete(employee.get());

                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Deleted");

            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no employee against this ID");
            }
            apiResponse.setData(null);
            return apiResponse;

        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    // The employee will tell how many hour will the work take. So after order is saved then the id of employee with order id needed to put
    // the working hours in the database and calculate the price
    public ApiResponse addNewWorkLog(String email, WorkLog workLog) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            Optional<Employee> emp = employeeRepo.findEmployeeByEmail(email);
            if (emp.isPresent()) {
                workLog.setDate(LocalDate.now());
                Optional<Order> order = orderRepo.findById(workLog.getOrder().getOrderId());
                if (order.isPresent()) {
                    workLog.setOrder(order.get());
                }
                emp.get().getWorkLogList().add(workLog);
                employeeRepo.save(emp.get());

                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Work log added");
                apiResponse.setData(emp.get());

                return apiResponse;
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no employee against this ID");
                return apiResponse;
            }
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    //WorkLog will only be shown by the email.
    public ApiResponse showWorkLog(String email) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            Optional<Employee> employee = employeeRepo.findEmployeeByEmail(email);

            if (employee.isPresent()) {
                if (employee.get().getType().equalsIgnoreCase("admin")) {
                    apiResponse.setStatus(HttpStatus.OK.value());
                    apiResponse.setMessage("Successful");
                    apiResponse.setData(workLogRepo.findAll());
                    return apiResponse;
                } else {
                    apiResponse.setStatus(HttpStatus.OK.value());
                    apiResponse.setMessage("Successful");
                    apiResponse.setData(employee.get().getWorkLogList());
                    return apiResponse;
                }

            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no user against this id");
                apiResponse.setData(null);
                return apiResponse;
            }
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }
}
