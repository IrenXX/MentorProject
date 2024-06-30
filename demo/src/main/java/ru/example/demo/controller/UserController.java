package ru.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.demo.dto.UserRequestDto;
import ru.example.demo.dto.UserRequestUpdateDto;
import ru.example.demo.dto.UserResponseDto;
import ru.example.demo.service.UserService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getUserById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUser(@RequestBody UserRequestUpdateDto updateDto) {
        return userService.updateUser(updateDto);
    }

}
