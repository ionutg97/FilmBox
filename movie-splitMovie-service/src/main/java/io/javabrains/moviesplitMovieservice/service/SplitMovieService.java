package io.javabrains.moviesplitMovieservice.service;

import io.javabrains.moviesplitMovieservice.exception.InvalidPathException;
import io.javabrains.moviesplitMovieservice.models.Chunck;
import io.javabrains.moviesplitMovieservice.models.SplitMovie;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SplitMovieService {

    public SplitMovie splitMovie(String pathName) {
        SplitMovie splitMovie = new SplitMovie();
        //---how find good size for chunck files---------------------------------------------
        splitMovie.setChunckSize((long) 1024 * 1024 * 5);
        //-------------------------------------------------------
        splitMovie.setNumberOfFiles(0);
        splitMovie.setTotalSizeFile((long) 0);
        splitMovie.generateVideoIdStoredInMongoDB();

        File file = createFileIfPathNameIsValid(pathName);
        if (file != null) {
            try {
                BufferedInputStream bufferedInputStream = convertFileIntoBufferedInputStream(file);
                splitMovie.setFileName(file.getName());
                byte[] buffer = new byte[Math.toIntExact(splitMovie.getChunckSize())];
                List<Chunck> listOfChuncks= new ArrayList<Chunck>();
                int bytesAmount = 0;

                while ((bytesAmount = bufferedInputStream.read(buffer)) > 0) {
                    splitMovie.incrementNumberOfFiles();
                    String filePartName = String.format("%s.%03d", splitMovie.getFileName(), (int) splitMovie.getNumberOfFiles());
                    //-- save file in cloud or mongoDB
                    Chunck chunck = new Chunck(splitMovie.getVideoId(),  Base64.encodeBase64String(buffer));
                    listOfChuncks.add(chunck);
                    File newFile = new File(file.getParent(), filePartName);
                    try (FileOutputStream out = new FileOutputStream(newFile)) {
                        splitMovie.incrementTotalSize((long) bytesAmount);
                        out.write(buffer, 0, bytesAmount);
                    }
                }
                splitMovie.setListOfChunks(listOfChuncks);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return splitMovie;
    }

    private File createFileIfPathNameIsValid(String pathNameFile) {
        File file = new File(pathNameFile);
        if (file.canExecute())
            return file;
        else {
            log.warn("File with " + pathNameFile + " dosen't exists!");
            throw new InvalidPathException("File with " + pathNameFile + " dosen't exists!");
        }
    }



    private BufferedInputStream convertFileIntoBufferedInputStream(File file) throws FileNotFoundException {
        BufferedInputStream bufferedInputStream;
        FileInputStream fis = new FileInputStream(file);
        bufferedInputStream = new BufferedInputStream(fis);

        return bufferedInputStream;
    }

}
