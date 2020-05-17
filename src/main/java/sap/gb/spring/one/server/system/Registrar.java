package sap.gb.spring.one.server.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sap.gb.spring.one.server.model.User;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class Registrar {
    private ConcurrentHashMap<String, User> onlineUsers;

    @Autowired
    public Registrar(ConcurrentHashMap<String, User> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public ConcurrentHashMap<String, User> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnline(User user) {
        onlineUsers.put(user.getNickName(), user);
    }

    public void setOffline(String nick) {
        onlineUsers.remove(nick);
    }
}
