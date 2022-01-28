package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Service.ProductService;
import org.app.AirConditioningApplication.response.ApiResponse;
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
    public ApiResponse list() {
        return productService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam Long Id) {
        return productService.delete(Id);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam Long Id) {
        return productService.getById(Id);
    }

    //This api only take product id and quantity which will be added to current quantity of product
    @GetMapping("/productQuantity")
    public ApiResponse productQuantity(@PathVariable Long id, @PathVariable int quantity) {
        return productService.productQuantity(id, quantity);
    }
}
