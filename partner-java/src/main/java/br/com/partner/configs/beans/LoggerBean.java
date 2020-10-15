package br.com.partner.configs.beans;

import br.com.partner.PartnerJavaApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerBean {

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger(PartnerJavaApplication.class);
    }
}
