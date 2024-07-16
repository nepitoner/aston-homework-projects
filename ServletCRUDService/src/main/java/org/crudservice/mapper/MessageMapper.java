package org.crudservice.mapper;

import org.crudservice.dto.MessageDto;
import org.crudservice.entity.Message;


public class MessageMapper {
    public static MessageDto mapToDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setText(message.getText());
        messageDto.setUserId(message.getUser().getId());
        messageDto.setDatetime(message.getDatetime());
        return messageDto;
    }

    public static Message mapToEntity(MessageDto messageDto) {
        Message message = new Message();
        if (messageDto.getId() != null) {
            message.setId(messageDto.getId());
        }
        message.setText(messageDto.getText());
        message.setDatetime(messageDto.getDatetime());
        return message;
    }
}
