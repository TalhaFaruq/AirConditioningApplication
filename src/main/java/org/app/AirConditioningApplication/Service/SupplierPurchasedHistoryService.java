package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.SupplierPurchasedHistory;
import org.app.AirConditioningApplication.Repository.SupplierPurchasedHistoryRepository;
import org.app.AirConditioningApplication.Utilities.PdfSupplierPurchase;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
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
            apiResponse.setData(null);
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
                apiResponse.setMessage("There is no purchased history in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setData(null);

            } else {
                apiResponse.setMessage("Successful");
                apiResponse.setData(supplierPurchasedHistories);
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
                apiResponse.setMessage("There is no purchased history against this ID");
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

    public ApiResponse delete(String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<SupplierPurchasedHistory> supplierPurchasedHistory = supplierPurchasedHistoryRepository.findById(id);

            if (supplierPurchasedHistory.isPresent()) {
                supplierPurchasedHistory.get().setSupplierProducts(null);
                supplierPurchasedHistoryRepository.delete(supplierPurchasedHistory.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successful");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no purchased history against this ID");
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

    public ApiResponse pdfDownload(String id, int quantityToBuy) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<SupplierPurchasedHistory> supplierPurchasedHistory = supplierPurchasedHistoryRepository.findById(id);
            if (supplierPurchasedHistory.isPresent()) {
                PdfSupplierPurchase pdfSupplierPurchase = new PdfSupplierPurchase(supplierPurchasedHistory.get(), quantityToBuy);

                pdfSupplierPurchase.pdfdownload();
                apiResponse.setData(supplierPurchasedHistory);
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully downloaded the pdf");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no purchased history against this ID");
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

    public ResponseEntity<Object> downloadFile(String supplierOrderId) {
        try {
            Optional<SupplierPurchasedHistory> purchasedHistory = supplierPurchasedHistoryRepository.findById(supplierOrderId);
            if (purchasedHistory.isPresent()) {
                String path = Paths.get("").toAbsolutePath().toString();
                String downloadFolderPath = path + "/src/main/resources/downloads/SupplierOrders/";

                String filename = downloadFolderPath + "SupplierOrder " + purchasedHistory.get().getSupplierOrderId() + ".pdf";
                File file = new File(filename);
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                ResponseEntity<Object> result = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
                return result;
            } else {
                return new ResponseEntity<>("There is no file against this id", HttpStatus.NOT_FOUND);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
