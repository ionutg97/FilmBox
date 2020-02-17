CREATE TABLE IF NOT EXISTS movies (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    file_name VARCHAR(100) NOT NULL,
    number_chunks INTEGER NOT NULL,
    total_size INTEGER NOT NULL,
    chunk_size INTEGER NOT NULL,
    id_blob_storage BIGINT NOT NULL,
    PRIMARY KEY(id)
);