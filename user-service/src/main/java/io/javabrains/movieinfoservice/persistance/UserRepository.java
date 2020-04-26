package io.javabrains.movieinfoservice.persistance;


import io.javabrains.movieinfoservice.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.role = ?1")
    Iterable<User> findAllUsersByType(String type);

    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Optional<User> findByName(String name);

}

