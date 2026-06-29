package com.mechtech.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server entry point.
 * <p>
 * Once started, services (customer-service, document-service, etc.) will register here
 * using their configured {@code spring.application.name}.
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args);

        System.out.println("Eurke : Stred ................................");
    }
}

