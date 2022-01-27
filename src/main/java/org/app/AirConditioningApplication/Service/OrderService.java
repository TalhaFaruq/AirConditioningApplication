package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Repository.BudgetRepo;
import org.app.AirConditioningApplication.Repository.OrderRepo;
import org.app.AirConditioningApplication.Utilities.PdfOrderTable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final BudgetRepo budgetRepo;

    public OrderService(OrderRepo orderRepo, BudgetRepo budgetRepo) {
        this.orderRepo = orderRepo;
        this.budgetRepo = budgetRepo;
    }

    public ResponseEntity<Object> save(Order order) {
        try {
            orderRepo.save(order);
            return ResponseEntity.accepted().body(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> showAll() {
        try {
            List<Order> orderList = orderRepo.findAll();
            if (!orderList.isEmpty())
                return ResponseEntity.ok().body(orderList);
            else
                return ResponseEntity.ok().body("There are no orders");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    public ResponseEntity<Object> getById(Long Id) {
        try {
            Optional<Order> order = orderRepo.findById(Id);
            if (order.isPresent())
                return ResponseEntity.ok().body(order);
            else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<Order> order = orderRepo.findById(Id);
            if (order.isPresent()) {
                order.get().setService(null);
                order.get().setCustomer(null);
                order.get().setProductList(null);
                orderRepo.delete(order.get());
                return ResponseEntity.ok().body("Deleted");
            } else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    //when the order is conformed it will change into order
    public ResponseEntity<Object> budgetToOrder(Long id) {
        Optional<Budget> budget = budgetRepo.findById(id);
        if (budget.isPresent()) {
            Order order = new Order();
            budget.get().setBudgetStatus("Completed");
            List<Order> orderList = orderRepo.findAll();
            if (orderList.size()==0){
                order.setOrderId(1L);
            }else order.setOrderId((long) orderList.size() +1);
            order.setService((budget.get().getService()));
            order.setCustomer(budget.get().getCustomer());
            order.setProductList(new ArrayList<>(budget.get().getProductList()));
            order.setTotalPrice(budget.get().getTotalPrice());

            orderRepo.save(order);
            budgetRepo.save(budget.get());

            return ResponseEntity.ok().body("PDF Downloaded");
        }
        else return ResponseEntity.ok().body("Invalid ID");
    }

    //Must be called after order is saved in database with worklog
    public ResponseEntity<Object> printPdf(Long id){
        //Pdf will be downloaded in downloads
        PdfOrderTable pdfOrderTable = new PdfOrderTable(orderRepo.findById(id).get());
        pdfOrderTable.pdfdownload();
        return ResponseEntity.accepted().body("Pdf downloaded");
    }
}
