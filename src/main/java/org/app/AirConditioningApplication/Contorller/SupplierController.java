package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Supplier;
import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.app.AirConditioningApplication.Service.SupplierService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return supplierService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam Long Id) {
        return supplierService.delete(Id);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam Long Id) {
        return supplierService.getById(Id);
    }

    @PostMapping("/addProductsInSupplier")
    public ApiResponse addMultipleProductsInSupplier(@RequestParam(name = "supplierId") Long supplierId, @RequestBody List<SupplierProduct> supplierProducts) {
        return supplierService.addMultipleProductsInSupplier(supplierId, supplierProducts);
    }

    @PostMapping("/addSingleProductInSupplier")
    public ApiResponse addSingleProductInSupplier(@RequestParam(name = "supplierId") Long supplierId, @RequestBody SupplierProduct supplierProduct) {
        return supplierService.addSingleProductInSupplier(supplierId, supplierProduct);
    }


    @PostMapping("/buyProductFromSupplier")
    @ResponseBody
    public ApiResponse buyProductFromSupplier(@RequestParam(name = "product") Long supplierProductId, @RequestParam(name = "quantity") Integer quantityToBuy) {
        return supplierService.buyProductsFromSupplier(supplierProductId, quantityToBuy);
    }

    @PostMapping("/buyMultipleProductFromSupplier")
    @ResponseBody
    public ApiResponse buyMultipleProductFromSupplier(@RequestBody List<Supplier> supplierList) {
        return supplierService.buyMultipleProductsFromSupplier(supplierList);
    }

}
