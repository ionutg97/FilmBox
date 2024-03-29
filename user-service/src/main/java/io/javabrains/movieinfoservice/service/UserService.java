package io.javabrains.movieinfoservice.service;

import io.javabrains.movieinfoservice.exceptions.ResourceNotFoundException;
import io.javabrains.movieinfoservice.models.User;
import io.javabrains.movieinfoservice.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName()));
    }

    public Iterable<User> findAllUserByType(String type) {
        return userRepository.findAllUsersByType(type);
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName()));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName()));
        userRepository.delete(user);
    }
}
