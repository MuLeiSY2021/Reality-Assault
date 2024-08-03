package indi.muleisy.ra.service.ebusiness.promotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableCaching
public class ElectricBusinessPromotionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElectricBusinessPromotionServiceApplication.class, args);
    }
}