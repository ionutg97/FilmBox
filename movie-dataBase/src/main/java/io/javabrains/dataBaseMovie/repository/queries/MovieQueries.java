package io.javabrains.dataBaseMovie.repository.queries;

public class MovieQueries {
    public static final String INSERT_MOVIE = " INSERT INTO MOVIES " +
            "(" +
            "file_name, " +
            "number_chunks, " +
            "total_size, " +
            "chunk_size, " +
            "id_blob_storage," +
            "video_id,"+
            "id_user" +
            ") "+
            "VALUES " +
            "( " +
            ":file_name, " +
            ":number_chunks," +
            ":total_size," +
            ":chunk_size," +
            ":id_blob_storage," +
            ":video_id,"+
            ":id_user" +
            " )";

    public static final String GET_ALL_MOVIE = "SELECT " +
            "id, " +
            "file_name, " +
            "number_chunks, " +
            "total_size, " +
            "video_id," +
            "id_user " +
            "FROM movies";

}
