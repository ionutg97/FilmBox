package io.javabrains.logginsserver.controller;

import io.javabrains.logginsserver.models.DataLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/loggs")
public class LoggsController {



    @PostMapping
    public ResponseEntity writeLog(@RequestBody DataLog dataLog) {

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(dataLog.getClassName().concat(".txt"), true)  );
            writer.newLine();
            writer.write(dataLog.getDate()+"  "+dataLog.getType()+" "+dataLog.getLogText());
            writer.close();
        } catch (IOException ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
