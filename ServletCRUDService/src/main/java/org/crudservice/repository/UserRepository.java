package org.crudservice.repository;

import org.crudservice.entity.Message;
import org.crudservice.entity.User;
import org.crudservice.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepository implements CRUDRepository<User> {
    @Override
    public User getById(Integer id) {
        User user = new User();
        String userQuery = "select * from user where id = ?";
        try (Connection con = DataSource.getConnection();
             PreparedStatement userPst = con.prepareStatement(userQuery)) {
            userPst.setInt(1, id);
            try (ResultSet usersRs = userPst.executeQuery()) {
                while (usersRs.next()) {
                    String sessionId = usersRs.getString("session_id");
                    String name = usersRs.getString("name");
                    user = new User();
                    user.setId(id);
                    user.setSessionId(sessionId);
                    user.setName(name);
                    List<Message> messages = getMessages(con, user);
                    user.setMessages(messages);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private List<Message> getMessages(Connection connection, User user) throws SQLException {
        String msgsQuery = "select * from message where user_id = ?";
        List<Message> messages = new ArrayList<>();
        try (PreparedStatement msgsPst = connection.prepareStatement(msgsQuery)) {
            msgsPst.setInt(1, user.getId());
            try (ResultSet msgsRs = msgsPst.executeQuery()) {
                while (msgsRs.next()) {
                    Message message = new Message();
                    message.setId(msgsRs.getInt("id"));
                    message.setText(msgsRs.getString("text"));
                    message.setDatetime(msgsRs.getDate("date_time").toLocalDate());
                    message.setUser(user);
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    private List<User> getUsers(ResultSet usersRs, Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        while (usersRs.next()) {
            int userId = usersRs.getInt("id");
            String sessionId = usersRs.getString("session_id");
            String name = usersRs.getString("name");

            User user = new User();
            user.setId(userId);
            user.setSessionId(sessionId);
            user.setName(name);
            List<Message> messages = getMessages(connection, user);
            user.setMessages(messages);
            users.add(user);
        }
        return users;
    }

    @Override
    public Collection<User> getAll() throws SQLException {
        String usersQuery = "select * from user";
        List<User> users;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement usersPst = connection.prepareStatement(usersQuery);
             ResultSet usersRs = usersPst.executeQuery()) {
            users = getUsers(usersRs, connection);
        }
        return users;
    }

    @Override
    public void save(User user) throws SQLException {
        String usersQuery = "insert into user (session_id, name) values (?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(usersQuery)) {
            pst.setString(1, user.getSessionId());
            pst.setString(2, user.getName());
            pst.executeUpdate();
        }
    }

    @Override
    public void update(User item) {
        String usQuery = "update user set name = ?, session_id = ? where id = ?";
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement pst = con.prepareStatement(usQuery);
            pst.setString(1, item.getName());
            pst.setString(2, item.getSessionId());
            pst.setInt(3, item.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String usQuery = "delete from user where id = ?";
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement pst = con.prepareStatement(usQuery);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
