package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Repository.OrderRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
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
                orderRepo.delete(order.get());
                return ResponseEntity.ok().body("Deleted");
            }else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
