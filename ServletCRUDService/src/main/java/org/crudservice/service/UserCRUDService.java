package org.crudservice.service;

import lombok.RequiredArgsConstructor;
import org.crudservice.dto.UserDto;
import org.crudservice.entity.User;
import org.crudservice.mapper.MessageMapper;
import org.crudservice.mapper.UserMapper;
import org.crudservice.repository.UserRepository;

import java.sql.SQLException;
import java.util.Collection;

@RequiredArgsConstructor
public class UserCRUDService implements CRUDService<UserDto> {

    private final UserRepository userRepository;

    @Override
    public UserDto getById(Integer id) {
        return UserMapper.mapToDto(userRepository.getById(id));
    }

    @Override
    public Collection<UserDto> getAll() {
        try {
            return userRepository.getAll().stream().map(UserMapper::mapToDto).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(UserDto userDto) {
        User user = UserMapper.mapToEntity(userDto);
        try {
            userRepository.save(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserDto userDto) {
        Integer userId = userDto.getId();
        User user = userRepository.getById(userId);
        user.setName(userDto.getName());
        user.setSessionId(userDto.getSessionId());
        user.setMessages(userDto.getMessages().stream().map(MessageMapper::mapToEntity).toList());
        userRepository.update(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
