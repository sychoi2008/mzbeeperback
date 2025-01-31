package com.example.mzbeeperback.domain.dictionary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "beeperdict")
@Entity
public class DictEntity {
    @Id
    int dict_id;

    String dict_msg;

    String dict_meant;

}
