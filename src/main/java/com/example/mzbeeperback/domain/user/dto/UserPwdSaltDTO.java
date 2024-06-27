package com.example.mzbeeperback.domain.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPwdSaltDTO {
    String hash_pwd;
    String salt;
}
