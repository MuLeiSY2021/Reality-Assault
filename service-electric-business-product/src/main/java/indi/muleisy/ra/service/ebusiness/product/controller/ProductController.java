package indi.muleisy.ra.service.ebusiness.product.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.product.model.Product;
import indi.muleisy.ra.service.ebusiness.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Result addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PostMapping("/restock")
    public Result restockProduct(@RequestParam String productId, @RequestParam int quantity) {
        return productService.restockProduct(productId, quantity);
    }

    @GetMapping("/summary/{keyword}")
    public Result queryProductSummary(@PathVariable String keyword) {
        return productService.queryProductSummary(keyword);
    }

    @GetMapping("/details/{productId}")
    public Result queryProductDetails(@PathVariable String productId) {
        return productService.queryProductDetails(productId);
    }
}
