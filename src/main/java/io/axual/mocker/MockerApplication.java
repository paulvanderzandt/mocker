package io.axual.mocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class MockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockerApplication.class, args);
    }

}
