package sap.gb.spring.one.server.model;


import lombok.Data;
import sap.gb.spring.one.server.send.Connection;

@Data
public class User {
    private int id;
    private String nickName;
    private String login;
    private String password;
    private Connection connection;
}
