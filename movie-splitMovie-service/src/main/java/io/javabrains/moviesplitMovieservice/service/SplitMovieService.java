package io.javabrains.moviesplitMovieservice.service;

import io.javabrains.moviesplitMovieservice.exception.InvalidPathException;
import io.javabrains.moviesplitMovieservice.models.SplitMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class SplitMovieService {

    public SplitMovie splitMovie1(String pathName)
    {
        SplitMovie splitMovie=new SplitMovie();
        //---how find good size for chunck files---------------------------------------------
        splitMovie.setChunckSize((long)1024*1024*5);
        //-------------------------------------------------------
        splitMovie.setNumberOfFiles(0);
        splitMovie.setTotalSizeFile((long)0);
        File file = createFileIfPathNameIsValid(pathName);
        if( file!=null) {
            try {
                BufferedInputStream bufferedInputStream = convertFileIntoBufferedInputStream(file);
                splitMovie.setFileName(file.getName());
                byte[] buffer = new byte[Math.toIntExact(splitMovie.getChunckSize())];
                int bytesAmount = 0;

                while ((bytesAmount = bufferedInputStream.read(buffer)) > 0) {
                    splitMovie.incrementNumberOfFiles();
                    String filePartName = String.format("%s.%03d", splitMovie.getFileName(), (int) splitMovie.getNumberOfFiles());
                    //-- save file in cloud ....
                    File newFile = new File(file.getParent(), filePartName);
                    try (FileOutputStream out = new FileOutputStream(newFile)) {
                        splitMovie.incrementTotalSize((long) bytesAmount);
                        out.write(buffer, 0, bytesAmount);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return splitMovie;
    }

    private  File createFileIfPathNameIsValid(String pathNameFile){
        File file= new File(pathNameFile);
        if(file.canExecute())
            return file;
        else {
            log.warn("File with "+pathNameFile+" dosen't exists!");
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
