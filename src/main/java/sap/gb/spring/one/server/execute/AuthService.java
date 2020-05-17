package sap.gb.spring.one.server.execute;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sap.gb.spring.one.server.model.User;
import sap.gb.spring.one.server.repo.UserRepo;

@Service
public class AuthService {
    private UserRepo userRepo;

    @Autowired
    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public synchronized User checkAuthData(String login, String password) {
       User user = userRepo.findUserByLogin(login);
       if (user != null && user.getPassword().equals(password)) {
           return user;
       } else {
           return null;
       }
    }
}
