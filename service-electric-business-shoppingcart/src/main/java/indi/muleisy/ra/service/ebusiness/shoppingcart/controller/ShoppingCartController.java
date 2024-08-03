package indi.muleisy.ra.service.ebusiness.report.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.Product;
import indi.muleisy.ra.service.ebusiness.report.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public Result addProductToCart(@RequestBody Product product, @RequestParam String userId) {
        return shoppingCartService.addProductToCart(userId, product);
    }

    @DeleteMapping("/remove")
    public Result removeProductFromCart(@RequestParam String userId, @RequestParam String productId) {
        return shoppingCartService.removeProductFromCart(userId, productId);
    }

    @GetMapping("/view")
    public Result viewCart(@RequestParam String userId) {
        return shoppingCartService.viewCart(userId);
    }

    @DeleteMapping("/removeByOrderId")
    public Result removeProductByOrderId(@RequestParam String userId, @RequestParam String orderId) {
        return shoppingCartService.removeProductByOrderId(userId, orderId);
    }
}
