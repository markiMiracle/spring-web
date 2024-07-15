package org.miracle.repository;


import lombok.AllArgsConstructor;
import org.miracle.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRepository {
    private Map<Long, User> users;
    private Long id = 0L;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    public void save(User user) {
        users.put(getNextId(), user);
    }
    public Optional<User> findById(Long id) {
        return Optional.of(users.get(id));
    }
    public List<User> getAllUsers() {
        var result = new ArrayList<User>();
        for(var user: users.entrySet()) {
            result.add(user.getValue());
        }
        return result;
    }
    public Optional<User> findByLogin(String login) {
        return getAllUsers().stream()
                .filter(entity -> entity.getLogin().equals(login))
                .findFirst();
    }
    public Long  getNextId() {
        this.id++;
        return id;
    }
}
