package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Service.BudgetService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin

@RestController
@RequestMapping("Budget")
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
        return budgetService.save(budget);
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
}
