package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Service.OrderService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("Order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return orderService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody Order order) {
        return orderService.save(order);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody Order order) {
        return orderService.save(order);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam Long Id) {
        return orderService.delete(Id);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam Long Id) {
        return orderService.getById(Id);
    }

    // When this api is called the pdf of order is also downloaded
    @GetMapping("/budgetToOrder")
    public ApiResponse budgetToOrder(@RequestParam Long Id) {
        return orderService.budgetToOrder(Id);
    }

    @GetMapping("/printPdfOrder")
    public ApiResponse pdfDownloader(@RequestParam Long Id) {
        return orderService.printPdf(Id);
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Object> downloadFile(@RequestParam(name = "orderId") Long orderId) {
        return orderService.downloadFile(orderId);
    }
}
