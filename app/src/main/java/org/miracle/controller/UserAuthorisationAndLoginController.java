package org.miracle.controller;


import lombok.AllArgsConstructor;
import org.miracle.dto.UserDTO;
import org.miracle.service.AuthorisationAndLoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;


@AllArgsConstructor
@RestController
public class UserAuthorisationAndLoginController {
    private AuthorisationAndLoginService service;

    @PostMapping("/users")
    public String create(@RequestBody UserDTO userDto) throws NoSuchAlgorithmException {
        service.getLogin(userDto);
        if (service.isSession()) {
            return "New user: " + userDto.getLogin() + " has been created!";
        }
        return "Your login or password are no valid, please try again!";
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable("id") String id, @RequestBody UserDTO userDto) throws NoSuchAlgorithmException {
        service.getAuthorisation(Long.parseLong(id), userDto);
        if (service.isSession()) {
            return "You has been login!";
        }
        return "Your login or password are no valid, please try again!";
    }
}
