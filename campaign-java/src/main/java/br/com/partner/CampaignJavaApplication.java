package br.com.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.partner"})
public class CampaignJavaApplication {

	public static void main(String[] args) {
		SpringApplication.	run(CampaignJavaApplication.class, args);
	}

}
