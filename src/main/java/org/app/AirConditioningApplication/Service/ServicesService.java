package org.app.AirConditioningApplication.Service;


import org.app.AirConditioningApplication.Model.Services;
import org.app.AirConditioningApplication.Repository.ServicesRepo;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesService {
    private final ServicesRepo servicesRepo;

    public ServicesService(ServicesRepo servicesRepo) {
        this.servicesRepo = servicesRepo;
    }

    public ApiResponse save(Services services) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            servicesRepo.save(services);
            apiResponse.setMessage("Successfully added the services in the database");
            apiResponse.setData(services);
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
            List<Services> servicesList = servicesRepo.findAll();
            if (!servicesList.isEmpty()) {
                apiResponse.setMessage("Successfully fetched the services list");
                apiResponse.setData(servicesList);
                apiResponse.setStatus(HttpStatus.OK.value());
            } else {
                apiResponse.setMessage("There is no services in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setData(null);
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
            Optional<Services> optionalServices = servicesRepo.findById(Id);
            if (optionalServices.isPresent()) {
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully fetched the Services");
                apiResponse.setData(optionalServices);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no Services in the database");
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
            Optional<Services> servicesOptional = servicesRepo.findById(Id);
            if (servicesOptional.isPresent()) {
                servicesRepo.delete(servicesOptional.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully Deleted the services");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no services against this ID");
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
