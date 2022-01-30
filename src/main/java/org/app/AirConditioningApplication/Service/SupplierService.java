package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Model.Supplier;
import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.app.AirConditioningApplication.Model.SupplierPurchasedHistory;
import org.app.AirConditioningApplication.Repository.ProductRepo;
import org.app.AirConditioningApplication.Repository.SupplierPurchasedHistoryRepository;
import org.app.AirConditioningApplication.Repository.SupplierRepo;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final SupplierRepo supplierRepo;
    private final ProductRepo productRepo;
    private final SupplierPurchasedHistoryRepository supplierPurchasedHistoryRepository;


    public SupplierService(SupplierRepo supplierRepo, ProductRepo productRepo, SupplierPurchasedHistoryRepository supplierPurchasedHistoryRepository) {
        this.supplierRepo = supplierRepo;
        this.productRepo = productRepo;
        this.supplierPurchasedHistoryRepository = supplierPurchasedHistoryRepository;
    }

    public ApiResponse showAll() {
        ApiResponse apiResponse = new ApiResponse();

        try {
            List<Supplier> supplierList = supplierRepo.findAll();
            if (!supplierList.isEmpty()) {
                apiResponse.setMessage("Successful");
                apiResponse.setData(supplierList);
                apiResponse.setStatus(HttpStatus.OK.value());
            } else {
                apiResponse.setMessage("There is no suppliers in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setData(null);
            }
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    public ApiResponse save(Supplier supplier) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            supplierRepo.save(supplier);
            apiResponse.setMessage("Supplier Successfully added in the database");
            apiResponse.setData(supplier);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse buyProductsFromSupplier(Supplier supplier, Integer quantityToBuy) {
        ApiResponse apiResponse = new ApiResponse();

        Optional<Product> optionalProduct = productRepo.findByName(supplier.getSupplierProducts().get(0).getName());
        if (optionalProduct.isPresent()) {
            optionalProduct.get().setQuantityInStock(optionalProduct.get().getQuantityInStock() + quantityToBuy);
            productRepo.save(optionalProduct.get());
            apiResponse.setMessage("Successfully updated the stock in products");
        } else {
            Product product = new Product();
            product.setName(supplier.getSupplierProducts().get(0).getName());
            product.setQuantityInStock(quantityToBuy);
            product.setTax(supplier.getSupplierProducts().get(0).getTax());

            product.setPrice(supplier.getSupplierProducts().get(0).getBasePrice() + ((supplier.getSupplierProducts().get(0).getTax() / 100) * supplier.getSupplierProducts().get(0).getBasePrice()));
            product.setCharacteristics(supplier.getSupplierProducts().get(0).getCharacteristics());
            productRepo.save(product);

            apiResponse.setMessage("Successfully purchased the product from supplier");
        }
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(supplier);
        return apiResponse;
    }

    public ApiResponse buyMultipleProductsFromSupplier(List<Supplier> supplierList) {
        ApiResponse apiResponse = new ApiResponse();

        SupplierPurchasedHistory purchasedHistory = new SupplierPurchasedHistory();

        for (Supplier supplier : supplierList
        ) {
            for (SupplierProduct supplierProduct : supplier.getSupplierProducts()
            ) {
                purchasedHistory.getSupplierProducts().add(supplierProduct);
                purchasedHistory.setTotalPrice(purchasedHistory.getTotalPrice() + supplierProduct.getBasePrice() + ((supplierProduct.getTax() / 100) * supplierProduct.getBasePrice()));
                Optional<Product> alreadyPresentProduct = productRepo.findByName(supplierProduct.getName());
                if (alreadyPresentProduct.isPresent()) {
                    alreadyPresentProduct.get().setQuantityInStock(alreadyPresentProduct.get().getQuantityInStock() + supplierProduct.getProductCount());
                    productRepo.save(alreadyPresentProduct.get());
                } else {
                    Product ourProduct = new Product();
                    ourProduct.setName(supplierProduct.getName());
                    ourProduct.setQuantityInStock(supplierProduct.getProductCount());
                    ourProduct.setTax(supplierProduct.getTax());
                    ourProduct.setPrice(supplierProduct.getBasePrice() + ((supplierProduct.getTax() / 100) * supplierProduct.getBasePrice()));
                    ourProduct.setCharacteristics(supplierProduct.getCharacteristics());
                    productRepo.save(ourProduct);
                }
            }

        }

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage("Products Purchased");
        apiResponse.setData(purchasedHistory);
        supplierPurchasedHistoryRepository.save(purchasedHistory);
        return apiResponse;
    }


    public ApiResponse getById(Long Id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<Supplier> supplier = supplierRepo.findById(Id);
            if (supplier.isPresent()) {
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully fetched the supplier");
                apiResponse.setData(supplier);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no supplier in the database");
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
            Optional<Supplier> supplier = supplierRepo.findById(Id);
            if (supplier.isPresent()) {
                supplier.get().setSupplierProducts(null);
                supplierRepo.delete(supplier.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully Deleted the supplier");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no supplier against this ID");
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
