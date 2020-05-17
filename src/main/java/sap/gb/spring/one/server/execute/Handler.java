package sap.gb.spring.one.server.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sap.gb.spring.one.server.send.Connection;
import sap.gb.spring.one.server.send.MessageSender;
import sap.gb.spring.one.server.system.Registrar;
import sap.gb.spring.one.server.model.User;

import java.io.IOException;
import java.util.concurrent.Callable;

@Component
@Scope("prototype")
public class Handler implements Callable<Boolean> {
    private User user;
    private Connection connection;
    private AuthService authService;
    private Registrar registrar;
    private MessageSender messageSender;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Boolean call() {
        if (authentication()) {
            user.setConnection(connection);
            registrar.setOnline(user);
            readMessages();
            registrar.setOffline(user.getNickName());
        }
        connection.close();
        return true;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setRegistrar(Registrar registrar) {
        this.registrar = registrar;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    private synchronized boolean authentication() {
        String[] authData;
        int loginIndex = 0;
        int passwordIndex = 1;
        String login;
        String password;

        while (true) {
            try {
                authData = connection.getDataInputStream().readUTF().split("\\s");
                if (authData.length < 2) {
                    if (authData[loginIndex].equals("/end")) {
                        messageSender.sendForClose(connection);
                        return false;
                    } else {
                        messageSender.sendAuthResponse(null, false, connection);
                        continue;
                    }
                }

                login = authData[loginIndex];
                password = authData[passwordIndex];

                user = authService.checkAuthData(login, password);
                if (user != null) {
                    messageSender.sendAuthResponse(user.getNickName(),true, connection);
                    break;
                } else {
                    messageSender.sendAuthResponse(null,false, connection);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private synchronized void readMessages() {
        String content = "";
        String nick;
        while (true) {
            try {
                content = user.getConnection().getDataInputStream().readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (content.startsWith("@")) {
                nick = content.substring(1, content.indexOf(" "));
                content = content.substring(nick.length()+1);
                messageSender.sendPrivate(nick, content);
            } else if (content.startsWith("/end")){
                messageSender.sendForClose(user.getConnection());
                break;
            } else {
                messageSender.sendPublic(user.getNickName(), content);
            }
        }
    }

}
