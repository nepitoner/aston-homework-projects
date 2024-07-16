package org.crudservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String sessionId;
    private String name;
    private List<Message> messages = List.of();
}

