package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Repository.BudgetRepo;
import org.app.AirConditioningApplication.Repository.OrderRepo;
import org.app.AirConditioningApplication.Utilities.PdfOrderTable;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final BudgetRepo budgetRepo;

    public OrderService(OrderRepo orderRepo, BudgetRepo budgetRepo) {
        this.orderRepo = orderRepo;
        this.budgetRepo = budgetRepo;
    }

    public ApiResponse save(Order order) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            orderRepo.save(order);
            apiResponse.setMessage("Successfully added in the database");
            apiResponse.setData(order);
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
            List<Order> orderList = orderRepo.findAll();
            if (!orderList.isEmpty()) {
                apiResponse.setMessage("Successfully fetched the order list");
                apiResponse.setData(orderList);
                apiResponse.setStatus(HttpStatus.OK.value());
            } else {
                apiResponse.setMessage("There is no employee in the database");
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
            Optional<Order> order = orderRepo.findById(Id);
            if (order.isPresent()) {
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successful");
                apiResponse.setData(order);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no order in the database");
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
            Optional<Order> order = orderRepo.findById(Id);
            if (order.isPresent()) {
                order.get().setService(null);
                order.get().setCustomer(null);
                order.get().setProductList(null);
                orderRepo.delete(order.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully Deleted");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no employee against this ID");
            }
            apiResponse.setData(null);
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    //when the order is conformed it will change into order
    public ApiResponse budgetToOrder(Long id) {
        ApiResponse apiResponse = new ApiResponse();

        Optional<Budget> budget = budgetRepo.findById(id);
        if (budget.isPresent()) {
            Order order = new Order();
            budget.get().setBudgetStatus("Completed");
            List<Order> orderList = orderRepo.findAll();
            /*if (orderList.size() == 0) {
                order.setOrderId(1L);
            } else {
                order.setOrderId((long) orderList.size() + 1);
            }*/
            order.setService((budget.get().getService()));
            order.setCustomer(budget.get().getCustomer());
            order.setProductList(new ArrayList<>(budget.get().getProductList()));
            order.setTotalPrice(budget.get().getTotalPrice());

            orderRepo.save(order);
            budgetRepo.save(budget.get());
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Successfully downloaded the pdf");
            apiResponse.setData(order);
            return apiResponse;
        } else {
            apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
            apiResponse.setMessage("There is no budget against this ID");
            return apiResponse;
        }
    }

    //Must be called after order is saved in database with worklog
    public ApiResponse printPdf(Long id) {
        ApiResponse apiResponse = new ApiResponse();
        //Pdf will be downloaded in downloads
        PdfOrderTable pdfOrderTable = new PdfOrderTable(orderRepo.findById(id).get());
        pdfOrderTable.pdfdownload();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage("Successfully downloaded the pdf");
        apiResponse.setData(orderRepo.findById(id).get());
        return apiResponse;
    }
}
