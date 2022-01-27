package org.app.AirConditioningApplication.Controller;

import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("Product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        return productService.showAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam Long Id) {
        return productService.delete(Id);
    }

    @GetMapping("/getByID")
    public ResponseEntity<Object> getById(@RequestParam Long Id) {
        return productService.getById(Id);
    }

    //This api only take product id and quantity which will be added to current quantity of product
    @GetMapping("/productQuantity")
    public ResponseEntity<Object> productQuantity(@PathVariable Long id,@PathVariable int quantity){
        return productService.productQuantity(id, quantity);
    }
}
