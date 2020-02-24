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

}
