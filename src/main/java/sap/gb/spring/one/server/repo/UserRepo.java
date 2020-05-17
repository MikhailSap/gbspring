package sap.gb.spring.one.server.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import sap.gb.spring.one.server.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class UserRepo {
    private DriverManagerDataSource dataSource;

    @Autowired
    public UserRepo(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public synchronized User findUserByLogin(String login) {
        User user = new User();
        try {
            Statement statement= dataSource.getConnection().createStatement();
            ResultSet resultSet = statement
                    .executeQuery("select * from usr where login= '" + login + "';");
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setNickName(resultSet.getString("nick_name"));
                user.setPassword(resultSet.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
