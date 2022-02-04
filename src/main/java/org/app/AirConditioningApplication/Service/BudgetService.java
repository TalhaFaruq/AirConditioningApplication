package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Customer;
import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Repository.BudgetRepo;
import org.app.AirConditioningApplication.Repository.CustomerRepo;
import org.app.AirConditioningApplication.Repository.ProductRepo;
import org.app.AirConditioningApplication.Utilities.PdfBudgetTable;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    private final BudgetRepo budgetRepo;
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;
    private final OrderService orderService;

    @Autowired
    public BudgetService(BudgetRepo budgetRepo, ProductRepo productRepo, CustomerRepo customerRepo, OrderService orderService) {
        this.budgetRepo = budgetRepo;

        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
        this.orderService = orderService;
    }

    public ApiResponse save(Budget budget) {
        ApiResponse apiResponse = new ApiResponse();


        try {
            budget.setBudgetId(null);

            Optional<Customer> customer = customerRepo.findById(budget.getCustomer().getCustomerId());
            if (!customer.isPresent()) {
//                budget.getCustomer().setCustomerId(null);
                Customer customer1 = new Customer();
                customer1.setName(budget.getCustomer().getName());
                customerRepo.save(customer1);
                budget.setCustomer(customer1);
            }

            //As the budget is Quotation, order is final receipt
            budget.setBudgetStatus("Pending");

            for (Product product : budget.getProductList()
            ) {
                budget.setTotalPrice(product.getPrice() + budget.getTotalPrice());
            }
            budget.setTotalPrice(budget.getTotalPrice() + budget.getOfficerHours() * 20 + budget.getAssistantHours() * 15);
            budgetRepo.save(budget);

            apiResponse.setMessage("Budget Successfully added in the database");
            apiResponse.setData(budget);
            apiResponse.setStatus(HttpStatus.OK.value());
            PdfBudgetTable pdfBudgetTable = new PdfBudgetTable(budget);
            pdfBudgetTable.pdfdownload();
//            pdfCall(budget.getBudgetId());
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    public ApiResponse update(Budget budget) {
        ApiResponse apiResponse = new ApiResponse();
        try {

            for (Product product : budget.getProductList()
            ) {
                budget.setTotalPrice(product.getPrice() + budget.getTotalPrice());
            }
            budgetRepo.save(budget);

            if (budget.getBudgetStatus().equalsIgnoreCase("accepted")) {
                orderService.budgetToOrder(budget.getBudgetId());
            }

            apiResponse.setMessage("Budget Successfully updated in the database");
            apiResponse.setData(budget);
            apiResponse.setStatus(HttpStatus.OK.value());

            return apiResponse;
        } catch (Exception e) {
            apiResponse.setData(null);
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
            apiResponse.setData(null);
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
            apiResponse.setData(null);
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
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    public ResponseEntity<Object> downloadFile(Long budgetId) {
        try {
            Optional<Budget> budget = budgetRepo.findById(budgetId);
            if (budget.isPresent()) {
                String path = Paths.get("").toAbsolutePath().toString();
                String downloadFolderPath = path + "/src/main/resources/downloads/Budgets/";

                String filename = downloadFolderPath + "Budget " + budget.get().getBudgetId() + ".pdf";
                File file = new File(filename);
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                ResponseEntity<Object> result = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
                return result;
            } else {
                return new ResponseEntity<>("There is no file against this id", HttpStatus.NOT_FOUND);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // For testing purpose (Test pass)
    public void pdfCall(Long budgetId) {
        PdfBudgetTable pdfBudgetTable = new PdfBudgetTable(budgetRepo.findById(budgetId).get());
        pdfBudgetTable.pdfdownload();
    }
}
