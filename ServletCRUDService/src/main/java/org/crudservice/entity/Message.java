package org.crudservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class Message {
    private int id;
    private LocalDate datetime;
    private String text;
    private User user;
}
