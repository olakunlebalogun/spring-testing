package com.olakunle;

import com.olakunle.model.Product;
import com.olakunle.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class UnitIntegrationTestApplication implements  CommandLineRunner, ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(UnitIntegrationTestApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Product one = new Product();
        one.setDescription("tall large bed");
        one.setProductName("Mouka foam");
        one.setPrice(20000.0);

        log.debug(one.toString());
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Product one = new Product();
        one.setDescription("50 inches black standing fan");
        one.setProductName("OX Standing fan");
        one.setPrice(8000.0);

        log.debug(one.toString());
    }
}
