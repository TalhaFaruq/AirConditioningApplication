package org.app.AirConditioningApplication.Service;


import org.app.AirConditioningApplication.Model.Supplier;
import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.app.AirConditioningApplication.Repository.SupplierProductRepo;
import org.app.AirConditioningApplication.Repository.SupplierRepo;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierProductService {
    private final SupplierProductRepo supplierProductRepo;
    private final SupplierRepo supplierRepo;

    public SupplierProductService(SupplierProductRepo supplierProductRepo, SupplierRepo supplierRepo) {
        this.supplierProductRepo = supplierProductRepo;
        this.supplierRepo = supplierRepo;
    }


    public ApiResponse save(SupplierProduct supplierProduct) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            supplierProductRepo.save(supplierProduct);
            apiResponse.setMessage("Successfully added the services in the database");
            apiResponse.setData(supplierProduct);
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
            List<SupplierProduct> supplierProducts = supplierProductRepo.findAll();
            if (!supplierProducts.isEmpty()) {
                apiResponse.setMessage("Successfully fetched the Supplier Product list");
                apiResponse.setData(supplierProducts);
                apiResponse.setStatus(HttpStatus.OK.value());
            } else {
                apiResponse.setMessage("There is no product in the database");
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
            Optional<SupplierProduct> supplierProduct = supplierProductRepo.findById(Id);
            if (supplierProduct.isPresent()) {
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully fetched the supplier product");
                apiResponse.setData(supplierProduct);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no supplier product in the database");
            }
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse deleteById(Long supplierProductId, Long supplierId) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<Supplier> supplier = supplierRepo.findById(supplierId);
            if (supplier.isPresent()) {
                List<SupplierProduct> products = supplier.get().getSupplierProducts();

                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getProductId().longValue() == supplierProductId.longValue()) {
                        products.remove(products.get(i));
                    }
                }
                supplier.get().setSupplierProducts(products);
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setData(supplier.get());
                supplierRepo.save(supplier.get());
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no Supplier against this ID");
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
            Optional<SupplierProduct> supplierProduct = supplierProductRepo.findById(Id);
            if (supplierProduct.isPresent()) {
                supplierProductRepo.delete(supplierProduct.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully Deleted the SupplierProduct");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no SupplierProduct against this ID");
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

