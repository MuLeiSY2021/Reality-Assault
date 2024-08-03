package indi.muleisy.ra.service.ebusiness.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableCaching
public class ElectricBusinessServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElectricBusinessServiceApplication.class, args);
    }
}