package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.WorkLog;
import org.app.AirConditioningApplication.Repository.WorkLogRepo;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkLogService {
    private final WorkLogRepo workLogRepo;

    public WorkLogService(WorkLogRepo workLogRepo) {
        this.workLogRepo = workLogRepo;
    }

    public ApiResponse save(WorkLog workLog) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            workLog.setDate(LocalDate.now());
            workLogRepo.save(workLog);

            apiResponse.setMessage("WorkLog Successfully added in the database");
            apiResponse.setData(workLog);
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
            List<WorkLog> workLogList = workLogRepo.findAll();
            if (workLogList.isEmpty()) {
                apiResponse.setMessage("There is no work log in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setData(null);
            } else {
                apiResponse.setMessage("Successful");
                apiResponse.setData(workLogList);
                apiResponse.setStatus(HttpStatus.OK.value());
            }
            return apiResponse;

        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    public ApiResponse getById(Long Id) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            Optional<WorkLog> workLog = workLogRepo.findById(Id);
            if (workLog.isPresent()) {
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully fetched the work log");
                apiResponse.setData(workLog);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no work log in the database");
            }
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse delete(Long Id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<WorkLog> workLog = workLogRepo.findById(Id);
            if (workLog.isPresent()) {
                workLog.get().setOrder(null);
                workLogRepo.delete(workLog.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully Deleted the work log");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no work log against this ID");
            }
            apiResponse.setData(null);
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }
}
