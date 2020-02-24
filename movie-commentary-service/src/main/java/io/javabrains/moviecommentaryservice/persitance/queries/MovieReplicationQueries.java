package io.javabrains.moviecommentaryservice.persitance.queries;

public class MovieReplicationQueries {

    public static final String INSERT_MOVIE = "INSERT INTO MOVIES (id) VALUES (:id)";

    public static final String DELETE_MOVIE = "DELETE FROM MOVIES WHERE id=:id";
}
