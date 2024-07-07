package ru.example.demo.service;

import ru.example.demo.dto.UserRequestDto;
import ru.example.demo.dto.UserRequestUpdateDto;
import ru.example.demo.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> findAll();

    UserResponseDto getUserById(Long id);

    Long createUser(UserRequestDto userRequestDto);

    void deleteUser(Long id);

    UserResponseDto updateUser(UserRequestUpdateDto updateDto);
}
