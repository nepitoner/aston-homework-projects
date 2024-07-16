package org.crudservice.servlet;

import lombok.RequiredArgsConstructor;
import org.crudservice.dto.UserDto;
import org.crudservice.repository.UserRepository;
import org.crudservice.service.UserCRUDService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet("/user")
@RequiredArgsConstructor
public class UserServlet extends HttpServlet {
    UserRepository userRepository = new UserRepository();
    UserCRUDService userCRUDService = new UserCRUDService(userRepository);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            UserDto userDto = userCRUDService.getById(Integer.parseInt(id));
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(userDto.toString());
            printWriter.close();
        } else {
            Collection<UserDto> usersDto = userCRUDService.getAll();
            PrintWriter printWriter = resp.getWriter();
            for (UserDto userDto : usersDto) {
                printWriter.write(userDto.toString() + "\n");
            }
            printWriter.close();
        }
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        UserDto userDto = new UserDto();
        userDto.setName(req.getParameter("name"));
        userDto.setSessionId(req.getParameter("session_id"));
        userCRUDService.create(userDto);
        resp.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        UserDto userDto = userCRUDService.getById(id);
        if (req.getParameter("name") != null) {
            userDto.setName(req.getParameter("name"));
        } else if (req.getParameter("session_id") != null) {
            userDto.setSessionId(req.getParameter("session_id"));
        }
        userCRUDService.update(userDto);
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        userCRUDService.deleteById(id);
        resp.setStatus(200);
    }
}