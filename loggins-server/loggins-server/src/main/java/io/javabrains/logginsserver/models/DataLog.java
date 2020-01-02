package io.javabrains.logginsserver.models;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataLog {
    private String className;
    private LocalDateTime date;
    private String type;
    private String logText;
}
