package io.javabrains.movieinfoservice.service;

import io.javabrains.movieinfoservice.exceptions.ResourceNotFoundException;
import io.javabrains.movieinfoservice.models.User;
import io.javabrains.movieinfoservice.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public static List<GrantedAuthority> getGrantedAuthority(User user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
    }


    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByName(email).orElseThrow(ResourceNotFoundException::new);

        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), getGrantedAuthority(user));
    }
}