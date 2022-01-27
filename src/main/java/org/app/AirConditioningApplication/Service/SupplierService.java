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
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Object> save(Supplier supplier) {
        try {
//            double total = 0;
            for (SupplierProduct product : supplier.getSupplierProducts()
            ) {
                product.setBasePrice(product.getBasePrice());
                /*total += product.getBasePrice();
                product.setTax(total * product.getTax());*/
            }

            supplierRepo.save(supplier);
            return ResponseEntity.accepted().body(supplier);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public void buyProductsFromSupplier(Supplier supplier, Integer quantityToBuy) {
        Optional<Product> optionalProduct = productRepo.findByName(supplier.getSupplierProducts().get(0).getName());
        if (optionalProduct.isPresent()) {
            optionalProduct.get().setQuantityInStock(optionalProduct.get().getQuantityInStock() + quantityToBuy);
            productRepo.save(optionalProduct.get());
        } else {
            Product product = new Product();
            product.setName(supplier.getSupplierProducts().get(0).getName());
            product.setQuantityInStock(quantityToBuy);
            product.setTax(supplier.getSupplierProducts().get(0).getTax());

            product.setPrice(supplier.getSupplierProducts().get(0).getBasePrice() + ((supplier.getSupplierProducts().get(0).getTax() / 100) * supplier.getSupplierProducts().get(0).getBasePrice()));
            product.setCharacteristics(supplier.getSupplierProducts().get(0).getCharacteristics());
            productRepo.save(product);
        }
    }

    public void buyMultipleProductsFromSupplier(List<Supplier> supplierList) {
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
    }


    public ResponseEntity<Object> showAll() {
        try {
            List<Supplier> supplierList = supplierRepo.findAll();
            if (!supplierList.isEmpty())
                return ResponseEntity.ok().body(supplierList);
            else
                return ResponseEntity.ok().body("There are no suppliers");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    public ResponseEntity<Object> getById(Long Id) {
        try {
            Optional<Supplier> supplier = supplierRepo.findById(Id);
            if (supplier.isPresent())
                return ResponseEntity.ok().body(supplier);
            else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<Supplier> supplier = supplierRepo.findById(Id);
            if (supplier.isPresent()) {
                supplier.get().setSupplierProducts(null);
                supplierRepo.delete(supplier.get());
                return ResponseEntity.ok().body("Deleted");
            } else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
