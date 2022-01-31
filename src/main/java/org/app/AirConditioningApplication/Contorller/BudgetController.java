package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Service.BudgetService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin

@RestController
@RequestMapping("Budget")
@EnableSwagger2
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return budgetService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody Budget budget) {
        return budgetService.save(budget);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody Budget budget) {
        return budgetService.update(budget);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam Long Id) {
        return budgetService.delete(Id);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam Long Id) {
        return budgetService.getById(Id);
    }

    @GetMapping("/exporttoPDF")
    public void exportToPDF(@RequestParam Long budgetId) {
        budgetService.pdfCall(budgetId);
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Object> downloadFile(@RequestParam(name = "budgetId") Long budgetId) {
        return budgetService.downloadFile(budgetId);
    }

}
