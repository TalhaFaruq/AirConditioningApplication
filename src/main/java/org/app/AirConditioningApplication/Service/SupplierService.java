package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Model.Supplier;
import org.app.AirConditioningApplication.Repository.SupplierRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final SupplierRepo supplierRepo;

    public SupplierService(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    public ResponseEntity<Object> save(Supplier supplier) {
        try {
            double total = 0;
            List<Product> productList = supplier.getProductSold();
            for (Product product: productList) {
                total += product.getPrice();
            }
            supplier.setTax(total * 0.1);
            supplierRepo.save(supplier);
            return ResponseEntity.accepted().body(supplier);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
            if(supplier.isPresent())
                return ResponseEntity.ok().body(supplier);
            else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<Supplier> supplier = supplierRepo.findById(Id);
            if(supplier.isPresent()){
                supplier.get().setProductSold(null);
                supplierRepo.delete(supplier.get());
                return ResponseEntity.ok().body("Deleted");
            }else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
