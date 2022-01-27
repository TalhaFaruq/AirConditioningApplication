package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Service.BudgetService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> list() {
        return budgetService.showAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Budget budget) {
        return budgetService.save(budget);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Budget budget) {
        return budgetService.save(budget);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam Long Id) {
        return budgetService.delete(Id);
    }

    @GetMapping("/getByID")
    public ResponseEntity<Object> getById(@RequestParam Long Id) {
        return budgetService.getById(Id);
    }

    @GetMapping("/exporttoPDF")
    public void exportToPDF(@RequestParam Long budgetId){
        budgetService.pdfCall(budgetId);
    }
}
