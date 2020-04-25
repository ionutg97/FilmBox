package io.javabrains.movieinfoservice.persistance;


import io.javabrains.movieinfoservice.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.role = ?1")
    Iterable<User> findAllUsersByType(String type);
}

