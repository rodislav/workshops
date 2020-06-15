package org.example.classicalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

//Programmatic transaction management
//https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch11s06.html
//https://www.baeldung.com/spring-programmatic-transaction-management
@Configuration
public class TransactionTemplateConfig {

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager manager) {
        return new TransactionTemplate(manager);
    }

}
