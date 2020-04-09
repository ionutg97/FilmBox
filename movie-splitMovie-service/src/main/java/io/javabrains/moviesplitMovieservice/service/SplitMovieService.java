package io.javabrains.moviesplitMovieservice.service;

import io.javabrains.moviesplitMovieservice.exception.InvalidPathException;
import io.javabrains.moviesplitMovieservice.models.Chunck;
import io.javabrains.moviesplitMovieservice.models.SplitMovie;
import lombok.extern.slf4j.Slf4j;
import java.util.Base64;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Service
public class SplitMovieService {

    public SplitMovie splitMovie(String pathName) {
        SplitMovie splitMovie = new SplitMovie();
        //---how find good size for chunck files---------------------------------------------
        splitMovie.setChunckSize((long) 1024 * 1024 * 5);
        splitMovie.setNumberOfFiles(0);
        splitMovie.setTotalSizeFile((long) 0);

        byte[] buffer = new byte[Math.toIntExact(splitMovie.getChunckSize())];
        ArrayList<Chunck> listOfChuncks = new ArrayList<>();
        int bytesAmount = 0;

        splitMovie.generateVideoIdStoredInMongoDB();
        File file = createFileIfPathNameIsValid(pathName);
        splitMovie.setFileName(file.getName());
        try {
            if (file != null) {
                BufferedInputStream bufferedInputStream = convertFileIntoBufferedInputStream(file);
                while ((bytesAmount = bufferedInputStream.read(buffer, 0, splitMovie.getChunckSize().intValue())) > 0) {
                    String base64Buffer = Base64.getEncoder().encodeToString(buffer);
                    Chunck chunck = new Chunck(null,splitMovie.getVideoId(), base64Buffer);
                    listOfChuncks.add(chunck);
                    splitMovie.incrementNumberOfFiles();
                }
                splitMovie.setListOfChunks(listOfChuncks);
            }
        }
        catch(IOException excep) {
            excep.printStackTrace();
        }
        return splitMovie;
    }

    public void saveChuncksInFile(SplitMovie splitMovie, String pathName) {
        int index=1;
        File file = createFileIfPathNameIsValid(pathName);
        for (Chunck item : splitMovie.getListOfChunks()) {
            String filePartName = String.format("%s.%03d", splitMovie.getFileName(), index);
            index++;
            File newFile = new File(file.getParent(), filePartName);

            try (FileOutputStream out = new FileOutputStream(newFile)) {
                out.write(item.getVideoChunck().getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SplitMovie compressBase64(SplitMovie splitMovie)
    {
        ListIterator<Chunck> chunckListIterator=splitMovie.getListOfChunks().listIterator();
        while(chunckListIterator.hasNext()) {
            Chunck chunck=chunckListIterator.next();
            String base64Buffer = Base64.getEncoder().encodeToString(chunck.getVideoChunck().getBytes());
            chunck.setVideoChunck(base64Buffer);
            chunckListIterator.set(chunck);
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


    private BufferedInputStream convertFileIntoBufferedInputStream(File file) {
        BufferedInputStream bufferedInputStream = null;
        try {

            FileInputStream fis = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fis);
        } catch (FileNotFoundException excep) {
            excep.printStackTrace();
        }
        return bufferedInputStream;
    }

}
