package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Model.Supplier;
import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.app.AirConditioningApplication.Model.SupplierPurchasedHistory;
import org.app.AirConditioningApplication.Repository.ProductRepo;
import org.app.AirConditioningApplication.Repository.SupplierProductRepo;
import org.app.AirConditioningApplication.Repository.SupplierPurchasedHistoryRepository;
import org.app.AirConditioningApplication.Repository.SupplierRepo;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplierService {
    private final SupplierRepo supplierRepo;
    private final ProductRepo productRepo;
    private final SupplierPurchasedHistoryRepository supplierPurchasedHistoryRepository;
    private final SupplierPurchasedHistoryService supplierPurchasedHistoryService;
    private final SupplierProductRepo supplierProductRepo;


    public SupplierService(SupplierRepo supplierRepo, ProductRepo productRepo, SupplierPurchasedHistoryRepository supplierPurchasedHistoryRepository, SupplierPurchasedHistoryService supplierPurchasedHistoryService, SupplierProductRepo supplierProductRepo) {
        this.supplierRepo = supplierRepo;
        this.productRepo = productRepo;
        this.supplierPurchasedHistoryRepository = supplierPurchasedHistoryRepository;
        this.supplierPurchasedHistoryService = supplierPurchasedHistoryService;
        this.supplierProductRepo = supplierProductRepo;
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
            supplier.setSupplierId(null);
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

    public ApiResponse buyProductsFromSupplier(Long supplierProductId, Integer quantityToBuy) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<SupplierProduct> supplierProduct = supplierProductRepo.findById(supplierProductId);
            if (supplierProduct.isPresent()) {
                Optional<Product> optionalProduct = productRepo.findByName(supplierProduct.get().getName());
                if (optionalProduct.isPresent()) {
                    optionalProduct.get().setQuantityInStock(optionalProduct.get().getQuantityInStock() + quantityToBuy);
                    productRepo.save(optionalProduct.get());
                    apiResponse.setMessage("Successfully updated the stock in products");
                } else {
                    Product product = new Product();
                    product.setProductId(null);
                    product.setName(supplierProduct.get().getName());
                    product.setQuantityInStock(quantityToBuy);
                    product.setTax(supplierProduct.get().getTax());

//                    product.setPrice(supplierProduct.get().getBasePrice() + ((supplierProduct.get().getTax() / 100) * supplierProduct.get().getBasePrice()));
                    product.setBasePrice(supplierProduct.get().getBasePrice());
                    product.setFinalPrice(supplierProduct.get().getBasePrice());
                    product.setCharacteristics(supplierProduct.get().getCharacteristics());
                    product.setProductQuantity(0);
                    product.setAddedToBudgetCart(true);
                    productRepo.save(product);
                    apiResponse.setMessage("Successfully purchased the new product from supplier");
                }
                SupplierPurchasedHistory supplierPurchasedHistory = new SupplierPurchasedHistory();
                supplierPurchasedHistory.setSupplierOrderId(UUID.randomUUID().toString());

                List<SupplierProduct> purchasedHistoryProductsList = supplierPurchasedHistory.getSupplierProducts();
                purchasedHistoryProductsList.add(supplierProduct.get());

                supplierPurchasedHistory.setSupplierProducts(purchasedHistoryProductsList);
                double totalTaxAmount = ((supplierProduct.get().getTax() / 100) * supplierProduct.get().getBasePrice()) * quantityToBuy;
                supplierPurchasedHistory.setTotalPrice((supplierProduct.get().getBasePrice() * quantityToBuy + totalTaxAmount));
                supplierPurchasedHistoryRepository.save(supplierPurchasedHistory);
                supplierPurchasedHistoryService.pdfDownload(supplierPurchasedHistory.getSupplierOrderId(), quantityToBuy);

                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setData(supplierProduct);

            } else {
                apiResponse.setMessage("There is no supplier Product against this ID in the database");
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
                    ourProduct.setProductId(null);
                    ourProduct.setName(supplierProduct.getName());
                    ourProduct.setQuantityInStock(supplierProduct.getProductCount());
                    ourProduct.setTax(supplierProduct.getTax());
                    ourProduct.setBasePrice(supplierProduct.getBasePrice() + ((supplierProduct.getTax() / 100) * supplierProduct.getBasePrice()));
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

    public ApiResponse addMultipleProductsInSupplier(Long supplierId, List<SupplierProduct> supplierProducts) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<Supplier> supplier = supplierRepo.findById(supplierId);
            if (supplier.isPresent()) {
                for (SupplierProduct product : supplierProducts
                ) {
                    List<SupplierProduct> supplierExistingProduct = supplier.get().getSupplierProducts();
                    if (supplierExistingProduct.isEmpty()) {

                    } else {
                        for (SupplierProduct existingProducts : supplierExistingProduct
                        ) {
                            if (existingProducts.getCharacteristics().equalsIgnoreCase(product.getCharacteristics()) && existingProducts.getName().equalsIgnoreCase(product.getName())) {
                                continue;
                            } else {
                                supplier.get().getSupplierProducts().add(product);
                            }
                        }
                    }
                }
                supplierRepo.save(supplier.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully added new products in the supplier");
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

    public ApiResponse addSingleProductInSupplier(Long supplierId, SupplierProduct supplierProduct) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<Supplier> supplier = supplierRepo.findById(supplierId);
            if (supplier.isPresent()) {
                supplier.get().getSupplierProducts().add(supplierProduct);
                supplierRepo.save(supplier.get());
                List<SupplierProduct> products = supplier.get().getSupplierProducts();
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully added new product in the supplier");
                apiResponse.setData(products.get(products.size() - 1));
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
}
