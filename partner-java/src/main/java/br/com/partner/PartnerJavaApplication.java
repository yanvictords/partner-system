package br.com.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"br.com.partner"})
public class PartnerJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartnerJavaApplication.class, args);
    }

}
