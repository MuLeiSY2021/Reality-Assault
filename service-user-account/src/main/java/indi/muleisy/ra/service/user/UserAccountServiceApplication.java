package indi.muleisy.ra.service.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableCaching
@ComponentScan(basePackages = {"indi.muleisy.ra.service.user", "indi.muleisy.ra.utils"})
public class UserAccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserAccountServiceApplication.class, args);
    }
}