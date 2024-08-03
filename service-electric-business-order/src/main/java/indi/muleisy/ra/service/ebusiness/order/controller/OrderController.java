package indi.muleisy.ra.service.ebusiness.order.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.order.model.Order;
import indi.muleisy.ra.service.ebusiness.order.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderManagementService orderManagementService;

    @PostMapping("/create")
    public Result createOrder(@RequestBody Order order) {
        return orderManagementService.createOrder(order);
    }

    @GetMapping("/view/{orderId}")
    public Result viewOrder(@PathVariable String orderId) {
        return orderManagementService.viewOrder(orderId);
    }

    @PutMapping("/modify")
    public Result modifyOrder(@RequestBody Order order) {
        return orderManagementService.modifyOrder(order);
    }
}
