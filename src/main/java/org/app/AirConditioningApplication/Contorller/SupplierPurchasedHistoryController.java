package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.SupplierPurchasedHistory;
import org.app.AirConditioningApplication.Service.SupplierPurchasedHistoryService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("supplierPurchasedHistory")

public class SupplierPurchasedHistoryController {
    private final SupplierPurchasedHistoryService supplierPurchasedHistoryService;

    public SupplierPurchasedHistoryController(SupplierPurchasedHistoryService supplierPurchasedHistoryService) {
        this.supplierPurchasedHistoryService = supplierPurchasedHistoryService;
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return supplierPurchasedHistoryService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody SupplierPurchasedHistory supplierPurchasedHistory) {
        return supplierPurchasedHistoryService.save(supplierPurchasedHistory);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody SupplierPurchasedHistory supplierPurchasedHistory) {
        return supplierPurchasedHistoryService.save(supplierPurchasedHistory);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam String id) {
        return supplierPurchasedHistoryService.delete(id);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam String id) {
        return supplierPurchasedHistoryService.getById(id);
    }

    @GetMapping("/downloadPdf")
    public ApiResponse downloadSupplierPurchasedHistory(@RequestParam(name = "id") String id) {
        return supplierPurchasedHistoryService.pdfDownload(id);
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Object> downloadFile(@RequestParam(name = "supplierBudgetId") String supplierBudgetId) {
        return supplierPurchasedHistoryService.downloadFile(supplierBudgetId);
    }

}
