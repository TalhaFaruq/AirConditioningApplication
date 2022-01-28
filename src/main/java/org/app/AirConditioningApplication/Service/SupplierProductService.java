
package org.app.AirConditioningApplication.Service;

/*
import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.app.AirConditioningApplication.Repository.SupplierProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierProductService {
    private final SupplierProductRepo supplierProductRepo;

    public SupplierProductService(SupplierProductRepo supplierProductRepo) {
        this.supplierProductRepo = supplierProductRepo;
    }


    public ResponseEntity<Object> save(SupplierProduct supplierProduct) {
        try {
            supplierProductRepo.save(supplierProduct);

            return ResponseEntity.accepted().body(supplierProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> showAll() {
        try {
            List<SupplierProduct> supplierProductList = supplierProductRepo.findAll();
            if (!supplierProductList.isEmpty())
                return ResponseEntity.ok().body(supplierProductList);
            else
                return ResponseEntity.ok().body("There are no products");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    public ResponseEntity<Object> getById(Long Id) {
        try {
            Optional<SupplierProduct> supplierProduct = supplierProductRepo.findById(Id);
            if (supplierProduct.isPresent())
                return ResponseEntity.ok().body(supplierProduct);
            else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<SupplierProduct> supplierProduct = supplierProductRepo.findById(Id);
            if (supplierProduct.isPresent()) {
                supplierProductRepo.delete(supplierProduct.get());
                return ResponseEntity.ok().body("Deleted");
            } else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
*/
