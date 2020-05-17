package sap.gb.spring.one.server;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sap.gb.spring.one.server.system.Server;


@ComponentScan("sap.gb.spring.one.server")
public class ChatApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        Server server = applicationContext.getBean("server", Server.class);
        server.start();

//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ChatApplication.class);
//        Server server = applicationContext.getBean(Server.class);
//        server.start();

    }



}
