package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.*;
import org.app.AirConditioningApplication.Repository.BudgetRepo;
import org.app.AirConditioningApplication.Repository.OrderRepo;
import org.app.AirConditioningApplication.Repository.WorkLogRepo;
import org.app.AirConditioningApplication.Utilities.PdfOrderTable;
import org.app.AirConditioningApplication.response.ApiResponse;
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
public class OrderService {
    private final OrderRepo orderRepo;
    private final BudgetRepo budgetRepo;
    private final WorkLogRepo workLogRepo;

    public OrderService(OrderRepo orderRepo, BudgetRepo budgetRepo, WorkLogRepo workLogRepo) {
        this.orderRepo = orderRepo;
        this.budgetRepo = budgetRepo;
        this.workLogRepo = workLogRepo;
    }

    public ApiResponse save(Order order) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            orderRepo.save(order);
            apiResponse.setMessage("Order successfully added in the database");
            apiResponse.setData(order);
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
            List<Order> orderList = orderRepo.findAll();
            if (!orderList.isEmpty()) {
                apiResponse.setMessage("Successfully fetched the order list");
                apiResponse.setData(orderList);
                apiResponse.setStatus(HttpStatus.OK.value());
            } else {
                apiResponse.setMessage("There is no order in the database");
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
            apiResponse.setData(null);
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
                List<WorkLog> workLogList = workLogRepo.findAllByOrder_OrderId(order.get().getOrderId());
                /*if (workLog.isPresent()) {
                    workLog.get().setOrder(null);
                    workLogRepo.save(workLog.get());
                }*/
                if (!workLogList.isEmpty()) {
                    for (WorkLog work : workLogList
                    ) {
                        work.setOrder(null);
                        workLogRepo.save(work);
                    }
                }
                orderRepo.save(order.get());
                orderRepo.delete(order.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully Deleted the order");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no order against this ID");
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

    //when the order is conformed it will change into order
    public ApiResponse budgetToOrder(Long id) {
        ApiResponse apiResponse = new ApiResponse();

        Optional<Budget> budget = budgetRepo.findById(id);
        if (budget.isPresent()) {
            Order order = new Order();
            budget.get().setBudgetStatus("Accepted");

            List<Services> serviceList = budget.get().getService();
            for (Services service1 : serviceList
            ) {
                order.getService().add(service1);
            }
            order.setCustomer(budget.get().getCustomer());
            List<Product> productList = budget.get().getProductList();
            for (Product product : productList
            ) {
                order.getProductList().add(product);
            }

            order.setTotalPrice(budget.get().getTotalPrice());
            order.setOrderName(budget.get().getBudgetName());
            order.setEmpPrice(budget.get().getOfficerHours() * 20 + budget.get().getAssistantHours() * 15);
            orderRepo.save(order);
            budgetRepo.save(budget.get());
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Order Successfully created");
            apiResponse.setData(order);
            printPdf(order.getOrderId());
        } else {
            apiResponse.setData(null);
            apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
            apiResponse.setMessage("There is no order against this ID");
        }
        return apiResponse;
    }

    public ResponseEntity<Object> downloadFile(Long orderId) {
        try {
            Optional<Order> order = orderRepo.findById(orderId);
            if (order.isPresent()) {
                String path = Paths.get("").toAbsolutePath().toString();
                String downloadFolderPath = path + "/src/main/resources/downloads/CustomerOrders/";

                String filename = downloadFolderPath + "Order " + order.get().getOrderId() + ".pdf";
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
