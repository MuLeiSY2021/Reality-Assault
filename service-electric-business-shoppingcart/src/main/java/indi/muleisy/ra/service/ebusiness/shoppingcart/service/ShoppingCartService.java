package indi.muleisy.ra.service.ebusiness.report.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.CartItem;
import indi.muleisy.ra.service.ebusiness.report.model.Product;
import indi.muleisy.ra.service.ebusiness.report.model.ShoppingCart;
import indi.muleisy.ra.service.ebusiness.report.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public Result addProductToCart(String userId, Product product) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElse(new ShoppingCart());
        cart.setUserId(userId);

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(product.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + product.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(product.getProductId());
            newItem.setQuantity(product.getQuantity());
            newItem.setPrice(product.getPrice());
            cart.getItems().add(newItem);
        }

        shoppingCartRepository.save(cart);
        return Result.success();
    }

    public Result removeProductFromCart(String userId, String productId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        shoppingCartRepository.save(cart);
        return Result.success();
    }

    public Result viewCart(String userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return Result.success(cart);
    }

    public Result removeProductByOrderId(String userId, String orderId) {
        // 逻辑假设：orderId 对应某个 productId (或一组 productId)
        // 在这里你需要根据你的业务逻辑处理 orderId 和 productId 之间的关系
        String productId = getProductIdFromOrderId(orderId);
        return removeProductFromCart(userId, productId);
    }

    private String getProductIdFromOrderId(String orderId) {
        // TODO: 通过订单号获取对应的商品ID，暂时返回假数据
        return "sampleProductId";
    }
}
