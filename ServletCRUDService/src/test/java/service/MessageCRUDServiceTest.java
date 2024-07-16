package service;

import org.crudservice.dto.MessageDto;
import org.crudservice.entity.Message;
import org.crudservice.entity.User;
import org.crudservice.repository.MessageRepository;
import org.crudservice.repository.UserRepository;
import org.crudservice.service.MessageCRUDService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageCRUDServiceTest {
    private final MessageRepository messageRepository = Mockito.mock(MessageRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final MessageCRUDService messageCRUDService = new MessageCRUDService(messageRepository, userRepository);

    @Test
    @DisplayName("Getting message by id test")
    public void getByIdTest() {
        int messageId = 1;
        Message message = new Message();
        message.setId(messageId);
        message.setText("New text");
        User user = new User();
        user.setId(1);
        message.setUser(user);
        when(messageRepository.getById(messageId)).thenReturn(message);

        MessageDto messageDto = messageCRUDService.getById(messageId);
        assertEquals(messageId, messageDto.getId());
        verify(messageRepository, times(1)).getById(messageId);
    }

    @Test
    @DisplayName("Getting all messages test")
    public void getByAllTest() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setId(1);
        message.setText("New text");
        User user = new User();
        user.setId(1);
        message.setUser(user);
        messages.add(message);
        when(messageRepository.getAll()).thenReturn(messages);

        Collection<MessageDto> messagesDto = messageCRUDService.getAll();
        assertEquals(messages.size(), messagesDto.size());
        verify(messageRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Creating new message test")
    public void createTest() {
        MessageDto messageDto = new MessageDto();
        messageDto.setUserId(1);
        messageCRUDService.create(messageDto);
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    @DisplayName("Updating message test")
    public void updateTest() {
        int messageId = 1;
        Message message = new Message();
        message.setId(messageId);
        when(messageRepository.getById(messageId)).thenReturn(message);

        MessageDto userDto = new MessageDto();
        userDto.setId(messageId);
        messageCRUDService.update(userDto);
        verify(messageRepository, times(1)).update(any(Message.class));
    }

    @Test
    @DisplayName("Deleting message test")
    public void deleteTest() {
        int messageId = 1;
        messageCRUDService.deleteById(messageId);
        verify(messageRepository, times(1)).deleteById(messageId);
    }
}
