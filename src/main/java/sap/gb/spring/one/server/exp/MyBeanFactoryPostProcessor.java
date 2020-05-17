package sap.gb.spring.one.server.exp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory  beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

        for(String name: beanDefinitionNames) {
            System.out.println("=============="+ name);
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            System.out.println("&&&&&&&&&&&&&&" + beanDefinition.toString());
        }
    }
}
