package io.javabrains.moviecommentaryservice.persitance.queries;

public class CommentQueries {

    public static final String INSERT_COMMENT = " INSERT INTO COMMENTS " +
            "(" +
            "content, " +
            "id_user, " +
            "id_movie " +
            ") " +
            "VALUES " +
            "( " +
            ":content, " +
            ":id_user," +
            ":id_movie" +
            " )";

    public static final String DELETE_COMMENT = " DELETE FROM COMMENTS WHERE id=:id";

    public static final String GET_COMMENT_FOR_MOVIE = "SELECT * FROM COMMENTS WHERE id_movie=:id";

    public static final String COUNT_NUMBER_OF_COMM_FOR_MOVIE = "SELECT COUNT(id) FROM COMMENTS WHERE id_movie=:id";
}
