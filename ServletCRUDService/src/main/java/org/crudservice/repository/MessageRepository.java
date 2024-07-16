package org.crudservice.repository;

import org.crudservice.dto.UserDto;
import org.crudservice.entity.Message;
import org.crudservice.mapper.UserMapper;
import org.crudservice.service.UserCRUDService;
import org.crudservice.util.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessageRepository implements CRUDRepository<Message> {
    private final UserCRUDService userCRUDService = new UserCRUDService(new UserRepository());

    @Override
    public Message getById(Integer id) {
        Message message = new Message();
        String mesQuery = "select * from message where id = ?";
        try (Connection con = DataSource.getConnection();
             PreparedStatement mesPst = con.prepareStatement(mesQuery)) {
            mesPst.setInt(1, id);

            try (ResultSet mesRs = mesPst.executeQuery()) {
                while (mesRs.next()) {
                    String text = mesRs.getString("text");
                    LocalDate datetime = mesRs.getDate("date_time").toLocalDate();
                    Integer userId = mesRs.getInt("user_id");

                    message.setText(text);
                    message.setDatetime(datetime);
                    message.setId(id);
                    message.setUser(UserMapper.mapToEntity(userCRUDService.getById(userId)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return message;
    }


    @Override
    public Collection<Message> getAll() {
        String msgQuery = "select * from message";
        List<Message> msgs = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement msgPst = connection.prepareStatement(msgQuery);
             ResultSet msgRs = msgPst.executeQuery()) {
            while (msgRs.next()) {
                Message message = new Message();
                message.setId(msgRs.getInt("id"));
                message.setText(msgRs.getString("text"));
                message.setDatetime(msgRs.getDate("date_time").toLocalDate());
                UserDto userDto = userCRUDService.getById(msgRs.getInt("user_id"));
                message.setUser(UserMapper.mapToEntity(userDto));
                msgs.add(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return msgs;
    }

    @Override
    public void save(Message message) {
        String msgQuery = "insert into message (text, user_id, date_time) values (?, ?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(msgQuery)) {
            pst.setString(1, message.getText());
            pst.setInt(2, message.getUser().getId());
            pst.setDate(3, Date.valueOf(message.getDatetime()));
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Message item) {
        String msgQuery = "update message set text = ?, user_id = ?, date_time = ? where id = ?";
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement pst = con.prepareStatement(msgQuery);
            pst.setString(1, item.getText());
            pst.setInt(2, item.getUser().getId());
            pst.setDate(3, Date.valueOf(item.getDatetime()));
            pst.setInt(4, item.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String msgQuery = "delete from message where id = ?";
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement pst = con.prepareStatement(msgQuery);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}