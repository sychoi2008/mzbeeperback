package com.example.mzbeeperback.domain.user.controller.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpDTO {
    String name;
    String id;
    String hash_pwd;
    String salt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime create_date;
}
