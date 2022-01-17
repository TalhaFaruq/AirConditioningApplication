package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Supplier;
import org.app.AirConditioningApplication.Service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        return supplierService.showAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam Long Id) {
        return supplierService.delete(Id);
    }

    @GetMapping("/getByID")
    public ResponseEntity<Object> getById(@RequestParam Long Id) {
        return supplierService.getById(Id);
    }
}
