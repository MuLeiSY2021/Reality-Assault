package indi.muleisy.ra.service.ebusiness.product.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.ebusiness.product.model.Product;
import indi.muleisy.ra.service.ebusiness.product.repository.ProductRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public Result addProduct(Product product) {
        productRepository.save(product);
        elasticsearchOperations.save(product); // 索引到Elasticsearch
        return Result.success();
    }

    public Result restockProduct(String productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return Result.failure(ResultCode.USER_NOT_EXISTED);
        }

        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
        return Result.success();
    }

    public Result queryProductSummary(String keyword) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "name", "description"))
                .build();

        SearchHits<Product> productHits = elasticsearchOperations.search(searchQuery, Product.class);
        return Result.success(productHits);
    }

    public Result queryProductDetails(String productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return Result.failure(ResultCode.USER_NOT_EXISTED);
        }

        return Result.success(product);
    }
}
