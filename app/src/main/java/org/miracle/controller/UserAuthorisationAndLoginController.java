package org.miracle.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.miracle.dto.UserDTO;
import org.miracle.service.RegistrationAndLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserAuthorisationAndLoginController {

    private RegistrationAndLoginService service;

    @Autowired
    private HttpServletRequest request;

    @PostMapping
    public String registration(@Valid @RequestBody UserDTO userDto) {
        var session = request.getSession();
        service.registration(userDto);
        session.setAttribute("currentUser", userDto.getLogin());
        return "New user " +
                session.getAttribute("currentUser").toString() +
                " has been created!";
    }

    @GetMapping("/login")
    public String login(@RequestBody UserDTO userDto) {
        var session = request.getSession();
        session.setAttribute("session", service.authorisation(userDto));
        if (session.getAttribute("session") != null) {
            return "you are logged in to your account: " + userDto.getLogin();
        }
        return null;
    }
}
