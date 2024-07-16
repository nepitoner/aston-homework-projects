package org.crudservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private int id;
    private String name;
    private String sessionId;
    private List<MessageDto> messages = List.of();
}
