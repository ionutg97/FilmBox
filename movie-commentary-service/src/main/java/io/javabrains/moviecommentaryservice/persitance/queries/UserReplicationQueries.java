package io.javabrains.moviecommentaryservice.persitance.queries;

public class UserReplicationQueries {

    public static final String INSERT_USER = "INSERT INTO USERS (id) VALUES (:id)";

    public static final String DELETE_USER = "DELETE FROM USERS WHERE id=:id";
}
