package org.miracle.service;

import lombok.Getter;
import lombok.Setter;
import org.miracle.dto.UserDTO;
import org.miracle.model.User;
import org.miracle.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Component
@Getter
@Setter
public class AuthorisationAndLoginService {
    private UserRepository repository;
    private boolean session;

    public AuthorisationAndLoginService(UserRepository repository) {
        this.repository = repository;
    }
    public void getLogin(UserDTO userDto) throws NoSuchAlgorithmException {
        var noValidLogin = userDto.getLogin();
        var noValidPass = userDto.getPassword();

        if (loginConfirmation(noValidLogin) && passwordConfirmation(noValidPass)) {
            var user = new User();
            user.setLogin(noValidLogin);
            user.setPasswordHash(passwordHashing(noValidPass));
            repository.save(user);
            session = true;
        } else {
            session = false;
        }
    }

    public void getAuthorisation(Long id, UserDTO userDto) throws NoSuchAlgorithmException {
        var currentUser = repository.findByLogin(userDto.getLogin());
        if (repository.findById(id).isPresent() && currentUser.isPresent()) {
            session = currentUser.get()
                    .getPasswordHash()
                    .equals(passwordHashing(userDto.getPassword()));
        } else {
            session = false;
        }
    }

    private boolean loginConfirmation(String login) {
        return repository.findByLogin(login).isEmpty();
    }

    private boolean passwordConfirmation(String password) {
        return password.length() > 4;
    }

    private String passwordHashing(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hash = md.digest(password.getBytes());

        return Base64.getEncoder().encodeToString(hash);
    }
}
