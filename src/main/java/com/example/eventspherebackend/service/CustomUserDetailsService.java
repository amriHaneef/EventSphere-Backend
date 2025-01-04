package com.example.eventspherebackend.service;

import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load the user from the database (or in-memory storage)
        Users users = userRepository.findByUsername(username);

        if (users == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Return the user details as an instance of org.springframework.security.core.userdetails.User
        return new org.springframework.security.core.userdetails.User(
                users.getUsername(),
                users.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + users.getRole()))
        );
    }


}
