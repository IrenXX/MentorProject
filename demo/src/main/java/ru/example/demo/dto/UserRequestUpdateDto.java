package ru.example.demo.dto;

import lombok.Data;

@Data
public class UserRequestUpdateDto {

    private Long id;
    private String name;
    private String surname;
}
