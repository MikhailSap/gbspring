package sap.gb.spring.one.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import sap.gb.spring.one.server.execute.AuthService;
import sap.gb.spring.one.server.execute.Handler;
import sap.gb.spring.one.server.execute.HandlerExecutor;
import sap.gb.spring.one.server.model.User;
import sap.gb.spring.one.server.repo.UserRepo;
import sap.gb.spring.one.server.send.MessageSender;
import sap.gb.spring.one.server.system.Registrar;
import sap.gb.spring.one.server.system.Server;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class Config {

    @Bean
    public Server server(HandlerExecutor handlerExecutor) {
        return new Server(handlerExecutor);
    }

    @Bean
    public AuthService authService(UserRepo userRepo) {
        return new AuthService(userRepo);
    }

    @Bean
    public UserRepo userRepo(DriverManagerDataSource dataSource) {
        return new UserRepo(dataSource);
    }


    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/spring_chat");
        dataSource.setUsername("Mikhail");
        dataSource.setPassword("123");
        return dataSource;
    }

    @Bean
    public HandlerExecutor handlerExecutor() {
        return new HandlerExecutor();
    }

    @Bean
    @Scope("prototype")
    public Handler handler(AuthService authService, Registrar registrar, MessageSender messageSender) {
        Handler handler = new Handler();
        handler.setAuthService(authService);
        handler.setRegistrar(registrar);
        handler.setMessageSender(messageSender);
        return handler;
    }

    @Bean
    public Registrar registrar(ConcurrentHashMap<String, User> onlineUsers) {
        return new Registrar(onlineUsers);
    }

    @Bean
    public MessageSender messageSender(Registrar registrar) {
        return new MessageSender(registrar);
    }

    @Bean
    ConcurrentHashMap<String, User> onlineUsers() {
        return new ConcurrentHashMap<>();
    }
}
