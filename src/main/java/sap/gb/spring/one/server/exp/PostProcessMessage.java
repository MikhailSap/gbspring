package sap.gb.spring.one.server.exp;

import org.springframework.stereotype.Component;

@Component
@MyPostProcessorMessage
public class PostProcessMessage {

    public void print() {
        System.out.println("I'm here, in MessageBeanPostProcessor!!!");
    }
}
