package com.example.userapi.service;

import com.example.userapi.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public UserService() {
        users.add(new User(counter.getAndIncrement(), "Ana Silva", "ana@email.com", 25));
        users.add(new User(counter.getAndIncrement(), "Carlos Oliveira", "carlos@email.com", 30));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User createUser(User user) {
        boolean emailExists = users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));

        User newUser = new User();
        newUser.setId(counter.getAndIncrement());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setAge(user.getAge());

        users.add(newUser);
        return newUser;
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setAge(userDetails.getAge());
        }
        return user;
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public List<User> searchUsersByName(String name) {
        return users.stream()
                .filter(user -> user.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(user -> user.getEmail().contains("@"))
                .filter(user -> user.getAge() > 0)
                .collect(Collectors.toList());
    }
}