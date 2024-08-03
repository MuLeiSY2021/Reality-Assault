package indi.muleisy.ra.service.ebusiness.payment.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.order.model.Order;
import indi.muleisy.ra.service.ebusiness.payment.model.UserBalance;
import indi.muleisy.ra.service.ebusiness.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/checkBalance")
    public Result checkBalance(@RequestParam String userId, @RequestBody List<UserBalance> balanceCheckList) {
        return paymentService.checkBalance(userId, balanceCheckList);
    }

    @GetMapping("/getBalance")
    public Result getBalance(@RequestParam String userId) {
        return paymentService.getBalance(userId);
    }

    @PostMapping("/processOrderPayment")
    public Result processOrderPayment(@RequestBody Order order) {
        return paymentService.processOrderPayment(order);
    }
}
