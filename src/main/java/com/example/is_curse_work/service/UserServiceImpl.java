package com.example.is_curse_work.service;

import com.example.is_curse_work.dto.RegisterForm;
import com.example.is_curse_work.model.Role;
import com.example.is_curse_work.model.User;
import com.example.is_curse_work.repository.RoleRepository;
import com.example.is_curse_work.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository users, RoleRepository roles, PasswordEncoder encoder) {
        this.users = users;
        this.roles = roles;
        this.encoder = encoder;
    }

    @Override
    public User register(RegisterForm form) {
        if (users.findByEmail(form.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User u = new User();
        u.setEmail(form.getEmail());
        u.setName(form.getName());
        u.setRoom(form.getRoom());
        u.setNotifEmailOn(form.isNotifEmailOn());
        u.setNotifPushOn(form.isNotifPushOn());
        u.setPasswordHash(encoder.encode(form.getPassword()));

        Role userRole = roles.findByCode("USER");
        u.getRoles().add(userRole);

        return users.save(u);
    }
}

