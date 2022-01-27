package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Model.Supplier;
import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.app.AirConditioningApplication.Repository.ProductRepo;
import org.app.AirConditioningApplication.Repository.SupplierRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final SupplierRepo supplierRepo;
    private final ProductRepo productRepo;


    public SupplierService(SupplierRepo supplierRepo, ProductRepo productRepo) {
        this.supplierRepo = supplierRepo;
        this.productRepo = productRepo;
    }

    public ResponseEntity<Object> save(Supplier supplier) {
        try {
//            double total = 0;
            for (SupplierProduct product: supplier.getSupplierProducts()
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


    public void buyProductsFromSupplier(Supplier supplier, Integer quantityToBuy){
        Optional<Product> optionalProduct = productRepo.findByName(supplier.getSupplierProducts().get(0).getName());
        if(optionalProduct.isPresent()){
            optionalProduct.get().setQuantityInStock(optionalProduct.get().getQuantityInStock()+quantityToBuy);
            productRepo.save(optionalProduct.get());
        }else{
            Product product = new Product();
            product.setName(supplier.getSupplierProducts().get(0).getName());
            product.setQuantityInStock(quantityToBuy);
            product.setTax(supplier.getSupplierProducts().get(0).getTax());

            product.setPrice(supplier.getSupplierProducts().get(0).getBasePrice()+ ((supplier.getSupplierProducts().get(0).getTax()/100)*supplier.getSupplierProducts().get(0).getBasePrice()));
            product.setCharacteristics(supplier.getSupplierProducts().get(0).getCharacteristics());
            productRepo.save(product);
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
                supplier.get().setSupplierProducts(null);
                supplierRepo.delete(supplier.get());
                return ResponseEntity.ok().body("Deleted");
            }else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
