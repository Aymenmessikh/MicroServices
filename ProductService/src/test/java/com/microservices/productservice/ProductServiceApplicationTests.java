package com.microservices.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.oracle.OracleContainer;

@SpringBootTest
@Testcontainers
class ProductServiceApplicationTests {
    @Container
   static OracleContainer oracle = new OracleContainer("gvenzl/oracle-free:slim-faststart")
            .withDatabaseName("ProductService")
            .withUsername("PS")
            .withPassword("Orange123");


    @Test
    void contextLoads() {
    }

}
