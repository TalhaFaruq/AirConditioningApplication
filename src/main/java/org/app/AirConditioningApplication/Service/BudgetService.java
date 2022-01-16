package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Repository.BudgetRepo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class BudgetService {
    private final BudgetRepo budgetRepo;

    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public ResponseEntity<Object> save(Budget budget) {
        try {
            budgetRepo.save(budget);
            return ResponseEntity.accepted().body(budget);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> showAll() {
        try {
            List<Budget> budgetList = budgetRepo.findAll();
            if (!budgetList.isEmpty())
                return ResponseEntity.ok().body(budgetList);
            else
                return ResponseEntity.ok().body("There are no Budgets");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    public ResponseEntity<Object> getById(Long Id) {
        try {
            Optional<Budget> budget = budgetRepo.findById(Id);
            return ResponseEntity.ok().body(budget);
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<Budget> budget = budgetRepo.findById(Id);
            budgetRepo.delete(budget.get());
            return ResponseEntity.ok().body("Deleted");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
