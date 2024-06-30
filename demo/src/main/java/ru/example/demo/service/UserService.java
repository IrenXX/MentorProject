package ru.example.demo.service;

import ru.example.demo.dto.UserRequestDto;
import ru.example.demo.dto.UserRequestUpdateDto;
import ru.example.demo.dto.UserResponseDto;

public interface UserService {

    UserResponseDto getUserById(Long id);

    Long createUser(UserRequestDto userRequestDto);

    void deleteUser(Long id);

    UserResponseDto updateUser(UserRequestUpdateDto updateDto);
}
