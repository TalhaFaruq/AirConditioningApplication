package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Repository.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public ResponseEntity<Object> save(Product product) {
        try {
            productRepo.save(product);
            return ResponseEntity.accepted().body(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> showAll() {
        try {
            List<Product> productList = productRepo.findAll();
            if (!productList.isEmpty())
                return ResponseEntity.ok().body(productList);
            else
                return ResponseEntity.ok().body("There are no products");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    public ResponseEntity<Object> getById(Long Id) {
        try {
            Optional<Product> product = productRepo.findById(Id);
            if(product.isPresent())
                return ResponseEntity.ok().body(product);
            else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<Product> product = productRepo.findById(Id);
            if(product.isPresent()){
                productRepo.delete(product.get());
                return ResponseEntity.ok().body("Deleted");
            }else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    public ResponseEntity<Object> productQuantity(Long id, int quantity){
        try {
            Optional<Product> product = productRepo.findById(id);
            if(product.isPresent()){
                product.get().setQuantityInStock(product.get().getQuantityInStock()+quantity);
                productRepo.save(product.get());
                return ResponseEntity.ok().body(product);
            }else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
