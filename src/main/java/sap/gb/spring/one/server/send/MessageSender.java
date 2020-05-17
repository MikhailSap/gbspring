package sap.gb.spring.one.server.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sap.gb.spring.one.server.model.User;
import sap.gb.spring.one.server.system.Registrar;

import java.io.IOException;

@Service
public class MessageSender {
    private Registrar registrar;

    @Autowired
    public MessageSender(Registrar registrar) {
        this.registrar = registrar;
    }

    public synchronized void sendPublic(String nickFrom,String message) {
        registrar.getOnlineUsers().forEach((nick, user) -> {
            try {
                user
                        .getConnection()
                        .getDataOutputStream()
                        .writeUTF(nickFrom + ": " +message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void sendPrivate(String nick, String message) {
        try {
            User user = registrar
                             .getOnlineUsers()
                             .get(nick);
            if (user != null) {
                user
                        .getConnection()
                        .getDataOutputStream()
                        .writeUTF(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void sendAuthResponse(String nickName, Boolean state, Connection connection) {
        try {
            if (state) {
                connection.getDataOutputStream().writeUTF(nickName +" /authok");
            } else {
                connection.getDataOutputStream().writeUTF("Login or password is incorrect, please try again.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendForClose(Connection connection) {
        try {
            connection.getDataOutputStream().writeUTF("/q");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
