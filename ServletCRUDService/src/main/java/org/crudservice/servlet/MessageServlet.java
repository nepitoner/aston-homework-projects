package org.crudservice.servlet;

import lombok.RequiredArgsConstructor;
import org.crudservice.dto.MessageDto;
import org.crudservice.repository.MessageRepository;
import org.crudservice.repository.UserRepository;
import org.crudservice.service.MessageCRUDService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collection;

@WebServlet("/message")
@RequiredArgsConstructor
public class MessageServlet extends HttpServlet {
    MessageCRUDService messageCRUDService = new MessageCRUDService(new MessageRepository(), new UserRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            MessageDto messageDto = messageCRUDService.getById(Integer.parseInt(id));
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(messageDto.toString());
            printWriter.close();
        } else {
            Collection<MessageDto> messagesDto = messageCRUDService.getAll();
            PrintWriter printWriter = resp.getWriter();
            for (MessageDto messageDto : messagesDto) {
                printWriter.write(messageDto.toString() + "\n");
            }
            printWriter.close();
        }
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        MessageDto messageDto = new MessageDto();
        messageDto.setDatetime(LocalDate.now());
        messageDto.setText(req.getParameter("text"));
        messageDto.setUserId(Integer.parseInt(req.getParameter("user_id")));
        messageCRUDService.create(messageDto);
        resp.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        MessageDto messageDto = messageCRUDService.getById(id);
        if (req.getParameter("text") != null) {
            messageDto.setText(req.getParameter("text"));
        } else if (req.getParameter("user_id") != null) {
            messageDto.setUserId(Integer.parseInt(req.getParameter("user_id")));
        }
        messageCRUDService.update(messageDto);
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        messageCRUDService.deleteById(id);
        resp.setStatus(200);
    }
}
