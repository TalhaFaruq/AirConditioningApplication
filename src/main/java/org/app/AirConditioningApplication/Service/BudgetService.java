package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Repository.BudgetRepo;
import org.app.AirConditioningApplication.Repository.ProductRepo;
import org.app.AirConditioningApplication.Utilities.PdfBudgetTable;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
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

    public ApiResponse save(Budget budget) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            //As the budget is Quotation, order is final receipt
            budget.setBudgetStatus("Pending");
            for (Product product : budget.getProductList()
            ) {
                budget.setTotalPrice(product.getPrice() + budget.getTotalPrice());
            }
            budgetRepo.save(budget);

            apiResponse.setMessage("Budget Successfully added in the database");
            apiResponse.setData(budget);
            apiResponse.setStatus(HttpStatus.OK.value());

            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse showAll() {
        ApiResponse apiResponse = new ApiResponse();
        try {
            List<Budget> budgetList = budgetRepo.findAll();
            if (!budgetList.isEmpty()) {
                apiResponse.setMessage("Successfully fetched the budget list");
                apiResponse.setData(budgetList);
                apiResponse.setStatus(HttpStatus.OK.value());
            } else {
                apiResponse.setMessage("There is no budget in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setData(null);
            }
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse getById(Long Id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<Budget> budget = budgetRepo.findById(Id);
            if (budget.isPresent()) {
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successful");
                apiResponse.setData(budget);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no budget in the database");
            }
            return apiResponse;

        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }


    public ApiResponse delete(Long Id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<Budget> budget = budgetRepo.findById(Id);
            if (budget.isPresent()) {
                budget.get().setProductList(null);
                budget.get().setService(null);
                budget.get().setCustomer(null);
                budgetRepo.delete(budget.get());

                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Budget is Successfully Deleted");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no budget against this ID");
            }
            apiResponse.setData(null);
            return apiResponse;

        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    // For testing purpose (Test pass)
    public void pdfCall(Long budgetId) {
        PdfBudgetTable pdfBudgetTable = new PdfBudgetTable(budgetRepo.findById(budgetId).get());
        pdfBudgetTable.pdfdownload();
    }
}
