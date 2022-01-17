package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
