package org.crudservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessageDto {
    private Integer id;
    private String text;
    private LocalDate datetime;
    private Integer userId;
}
