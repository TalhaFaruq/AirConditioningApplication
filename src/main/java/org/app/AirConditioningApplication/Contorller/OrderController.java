package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        return orderService.showAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Order order) {
        return orderService.save(order);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Order order) {
        return orderService.save(order);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam Long Id) {
        return orderService.delete(Id);
    }

    @GetMapping("/getByID")
    public ResponseEntity<Object> getById(@RequestParam Long Id) {
        return orderService.getById(Id);
    }
}
