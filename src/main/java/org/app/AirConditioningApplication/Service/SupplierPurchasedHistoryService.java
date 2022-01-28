package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.SupplierPurchasedHistory;
import org.app.AirConditioningApplication.Repository.SupplierPurchasedHistoryRepository;
import org.app.AirConditioningApplication.Utilities.PdfSupplierPurchase;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SupplierPurchasedHistoryService {
    private final SupplierPurchasedHistoryRepository supplierPurchasedHistoryRepository;

    public SupplierPurchasedHistoryService(SupplierPurchasedHistoryRepository supplierPurchasedHistoryRepository) {
        this.supplierPurchasedHistoryRepository = supplierPurchasedHistoryRepository;
    }


    public ApiResponse save(SupplierPurchasedHistory supplierPurchasedHistory) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            supplierPurchasedHistoryRepository.save(supplierPurchasedHistory);
            apiResponse.setMessage("Successful");
            apiResponse.setData(supplierPurchasedHistory);
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
        List<SupplierPurchasedHistory> supplierPurchasedHistories = supplierPurchasedHistoryRepository.findAll();
        try {
            if (supplierPurchasedHistories.isEmpty()) {
                apiResponse.setMessage("There is no Purchased in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());

            } else {
                apiResponse.setMessage("Successful");
                apiResponse.setData(supplierPurchasedHistories);
                apiResponse.setStatus(HttpStatus.OK.value());
            }

            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse getById(String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<SupplierPurchasedHistory> supplierPurchasedHistory = supplierPurchasedHistoryRepository.findById(id);
            if (supplierPurchasedHistory.isPresent()) {
                apiResponse.setData(supplierPurchasedHistory);
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successful");

            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no supplierPurchasedHistory against this ID");
            }
            apiResponse.setData(null);
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse delete(String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<SupplierPurchasedHistory> supplierPurchasedHistory = supplierPurchasedHistoryRepository.findById(id);

            if (supplierPurchasedHistory.isPresent()) {
                supplierPurchasedHistory.get().setSupplierProducts(null);
                supplierPurchasedHistoryRepository.delete(supplierPurchasedHistory.get());
                apiResponse.setData(supplierPurchasedHistory);
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successful");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no supplierPurchasedHistory against this ID");
            }
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    public ApiResponse pdfDownload(String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<SupplierPurchasedHistory> supplierPurchasedHistory = supplierPurchasedHistoryRepository.findById(id);
            if (supplierPurchasedHistory.isPresent()) {
                PdfSupplierPurchase pdfSupplierPurchase = new PdfSupplierPurchase(supplierPurchasedHistory.get());

                pdfSupplierPurchase.pdfdownload();
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successful");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no supplierPurchasedHistory against this ID");
            }

            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


}
