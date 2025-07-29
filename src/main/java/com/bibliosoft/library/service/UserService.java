package com.bibliosoft.library.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.bibliosoft.library.dto.UserDTO;

@Service
public class UserService {
    private final Map<Long, UserDTO> users = new HashMap<>();
    private Long idCounter = 1L;

    public List<UserDTO> getAll() {
        return new ArrayList<>(users.values());
    }

    public UserDTO add(UserDTO user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
        return user;
    }

    public Optional<UserDTO> getById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public boolean delete(Long id) {
        return users.remove(id) != null;
    }
}
