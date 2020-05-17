package sap.gb.spring.one.server.exp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MessageBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(MyPostProcessorMessage.class)) {
            PostProcessMessage postProcessMessage = (PostProcessMessage) bean;
            postProcessMessage.print();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + " from postProcessAfterInitialization");
        return bean;
    }
}
