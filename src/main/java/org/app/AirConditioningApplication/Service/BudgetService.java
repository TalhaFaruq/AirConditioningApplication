package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Repository.BudgetRepo;
import org.app.AirConditioningApplication.Repository.ProductRepo;
import org.app.AirConditioningApplication.Utilities.PdfBudgetTable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    private final BudgetRepo budgetRepo;
    private final ProductRepo productRepo;



    public BudgetService(BudgetRepo budgetRepo, ProductRepo productRepo) {
        this.budgetRepo = budgetRepo;

        this.productRepo = productRepo;
    }

    public ResponseEntity<Object> save(Budget budget) {
        try {
            //As the budget is Quotation, order is final receipt
            budget.setBudgetStatus("Pending");
            if (!budget.getProductList().isEmpty()) {
                List<Product> products =  budget.getProductList();
                //this stream will get the sum of all the products
                for (Product product:products) {
                    Optional<Product> product1 = productRepo.findById(product.getProductId());
                    budget.setTotalPrice(budget.getTotalPrice() + product1.get().getPrice());
                }
//                budget.setTotalPrice(products.stream().mapToInt(Product::getPrice).sum());
//                budget.getProductList().stream().forEach(product -> product.setQuantityInStock(product.getQuantityInStock()-1));
            }

            // This will create pdf of budget

            budgetRepo.save(budget);
            PdfBudgetTable pdfBudgetTable = new PdfBudgetTable(budget);
            pdfBudgetTable.pdfdownload();
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
            if (budget.isPresent())
                return ResponseEntity.ok().body(budget);
            else return ResponseEntity.ok().body("Invalid Id");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<Budget> budget = budgetRepo.findById(Id);
            if (budget.isPresent()) {
                budget.get().setProductList(null);
                budget.get().setService(null);
                budget.get().setCustomer(null);
                budgetRepo.delete(budget.get());
                return ResponseEntity.ok().body("Deleted");
            } else return ResponseEntity.ok().body("Invalid ID");

        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    // For testing purpose (Test pass)
    public void pdfCall(){
        PdfBudgetTable pdfBudgetTable = new PdfBudgetTable(budgetRepo.findById(3L).get());
        pdfBudgetTable.pdfdownload();
    }
}
