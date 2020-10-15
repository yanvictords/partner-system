package br.com.partner.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanUtilsConfig {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(final ApplicationContext applicationContext) {
        setContext(applicationContext);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    private static void setContext(final ApplicationContext applicationContext) {
        BeanUtilsConfig.applicationContext = applicationContext;
    }
}
