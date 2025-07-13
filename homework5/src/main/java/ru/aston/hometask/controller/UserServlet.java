package ru.aston.hometask.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.hometask.exception.ValidationException;
import ru.aston.hometask.service.ServiceFactory;
import ru.aston.hometask.service.api.IUserService;
import ru.aston.hometask.service.dto.EGender;
import ru.aston.hometask.service.dto.UserCreateDto;
import ru.aston.hometask.service.dto.UserReadDto;
import ru.aston.hometask.validator.error.ValidationError;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    public static final String PARAMETER_FIRSTNAME = "firstname";
    public static final String PARAMETER_LASTNAME = "lastname";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_EMAIL = "email";
    public static final String PARAMETER_BIRTHDAY = "birthday";
    public static final String PARAMETER_GENDER = "gender";
    public static final String MESSAGE_ERROR = "Ошибки при вводе пользователя: ";
    public static final String MESSAGE_SUCCESS = "ID пользователя: ";
    private final IUserService userService = ServiceFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCreateDto user = UserCreateDto.builder()
                .setFirstname(req.getParameter(PARAMETER_FIRSTNAME))
                .setLastname(req.getParameter(PARAMETER_LASTNAME))
                .setPassword(req.getParameter(PARAMETER_PASSWORD))
                .setEmail(req.getParameter(PARAMETER_EMAIL))
                .setBirthdate(req.getParameter(PARAMETER_BIRTHDAY))
                .setGender(EGender.valueOf(req.getParameter(PARAMETER_GENDER)))
                .build();

        try {
            UserReadDto userRead = userService.create(user);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(MESSAGE_SUCCESS + userRead.getId().toString());
        } catch (ValidationException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String errors = exception.getErrors()
                    .stream()
                    .map(ValidationError::description)
                    .collect(Collectors.joining("\n"));
            resp.getWriter().write(MESSAGE_ERROR + "\n" + errors);
        }
    }
}
