package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.app.AirConditioningApplication.Service.SupplierProductService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("SupplierProduct")
public class SupplierProductController {
    private final SupplierProductService supplierProductService;

    public SupplierProductController(SupplierProductService supplierProductService) {
        this.supplierProductService = supplierProductService;
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return supplierProductService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody SupplierProduct supplierProduct) {
        return supplierProductService.save(supplierProduct);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody SupplierProduct supplierProduct) {
        return supplierProductService.save(supplierProduct);
    }

/*    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam Long Id) {
        return supplierProductService.delete(Id);
    }*/

    @DeleteMapping("/delete")
    public ApiResponse deleteById(@RequestParam(name = "productId") Long productId, @RequestParam(name = "supplierId") Long supplierId) {

        return supplierProductService.deleteById(productId, supplierId);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam Long Id) {
        return supplierProductService.getById(Id);
    }
}
