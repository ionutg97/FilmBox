package io.javabrains.dataBaseMovie.repository.queries;

public class MovieQueries {
    public static final String INSERT_MOVIE = " INSERT INTO MOVIES " +
            "(" +
            "file_name, " +
            "number_chunks, " +
            "total_size, " +
            "chunk_size, " +
            "id_blob_storage" +
            ") "+
            "VALUES " +
            "( " +
            ":file_name, " +
            ":number_chunks," +
            ":total_size," +
            ":chunk_size," +
            ":id_blob_storage" +
            " )";

}
