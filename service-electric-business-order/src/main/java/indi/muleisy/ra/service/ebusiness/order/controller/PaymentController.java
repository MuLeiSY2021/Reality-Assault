package indi.muleisy.ra.service.ebusiness.order.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public Result payOrder(@RequestParam String orderId, @RequestParam String paymentMethod, @RequestParam String paymentToken) {
        return paymentService.payOrder(orderId, paymentMethod, paymentToken);
    }
}
