package org.miracle.service;

import org.miracle.dto.UserDTO;

public interface RegistrationAndLoginService {
    void registration(UserDTO user);
    UserDTO authorisation(UserDTO user);
}
