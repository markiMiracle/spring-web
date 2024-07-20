package org.miracle.service;

import lombok.AllArgsConstructor;
import org.miracle.dto.UserDTO;
import org.miracle.exception.AuthorisationException;
import org.miracle.exception.UserNotFoundException;
import org.miracle.model.User;
import org.miracle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationAndLoginServiceImpl implements RegistrationAndLoginService {

    @Autowired
    private UserRepository repository;

    @Override
    public void registration(UserDTO userDto) {
        var user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        repository.save(user);
    }

    @Override
    public UserDTO authorisation(UserDTO userDto) {
        var currentUser = repository.findByLogin(userDto.getLogin()).orElseThrow(() -> new UserNotFoundException(userDto.getLogin()));
        if (currentUser.getPassword().equals(userDto.getPassword())) {
            return userDto;
        } else {
            throw new AuthorisationException("Authorisation has been failed");
        }
    }
}
