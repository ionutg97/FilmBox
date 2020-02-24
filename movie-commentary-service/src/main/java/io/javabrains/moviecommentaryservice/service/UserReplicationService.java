package io.javabrains.moviecommentaryservice.service;

import io.javabrains.moviecommentaryservice.models.User;
import io.javabrains.moviecommentaryservice.persitance.UserReplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserReplicationService {

    @Autowired
    private UserReplicationRepository userReplicationRepository;

    public User saveUserReplication(User user) {
        return userReplicationRepository.saveUserReplication(user);
    }

    public int deleteUserReplication(Long id) {
        return userReplicationRepository.deleteUserReplication(id);
    }
}
