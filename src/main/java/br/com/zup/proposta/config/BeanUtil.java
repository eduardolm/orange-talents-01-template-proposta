package br.com.zup.proposta.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = {"prod", "dev", "test"})
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    public BeanUtil(ApplicationContext context) {
        BeanUtil.context = context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
