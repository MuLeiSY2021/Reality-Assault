package indi.muleisy.ra.service.ebusiness.promotion.service;

import indi.muleisy.ra.service.ebusiness.promotion.model.Product;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionProcessingService {

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    public void indexProduct(Product product) {
        // 将商品信息索引到Elasticsearch中
        // 编写将product对象索引到Elasticsearch的方法
    }
}
