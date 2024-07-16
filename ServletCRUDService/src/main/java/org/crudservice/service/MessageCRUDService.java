package org.crudservice.service;

import lombok.RequiredArgsConstructor;
import org.crudservice.dto.MessageDto;
import org.crudservice.entity.Message;
import org.crudservice.mapper.MessageMapper;
import org.crudservice.repository.MessageRepository;
import org.crudservice.repository.UserRepository;

import java.util.Collection;

@RequiredArgsConstructor
public class MessageCRUDService implements CRUDService<MessageDto> {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public MessageDto getById(Integer id) {
        Message message = messageRepository.getById(id);
        return MessageMapper.mapToDto(message);
    }

    @Override
    public Collection<MessageDto> getAll() {
        return messageRepository.getAll()
                .stream()
                .map(MessageMapper::mapToDto)
                .toList();
    }

    @Override
    public void create(MessageDto messageDto) {
        Integer userId = messageDto.getUserId();
        Message message = MessageMapper.mapToEntity(messageDto);
        message.setUser(userRepository.getById(userId));
        messageRepository.save(message);
    }

    @Override
    public void update(MessageDto messageDto) {
        Integer userId = messageDto.getUserId();
        Message message = MessageMapper.mapToEntity(messageDto);
        message.setUser(userRepository.getById(userId));
        messageRepository.update(message);
    }

    @Override
    public void deleteById(Integer id) {
        messageRepository.deleteById(id);
    }
}
