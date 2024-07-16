package org.crudservice.mapper;

import org.crudservice.dto.UserDto;
import org.crudservice.entity.User;

public class UserMapper {
    public static User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSessionId(userDto.getSessionId());
        user.setMessages(userDto.getMessages()
                .stream()
                .map(MessageMapper::mapToEntity)
                .toList());
        return user;
    }

    public static UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSessionId(user.getSessionId());
        userDto.setMessages(user.getMessages()
                .stream()
                .map(MessageMapper::mapToDto)
                .toList());
        return userDto;
    }
}
