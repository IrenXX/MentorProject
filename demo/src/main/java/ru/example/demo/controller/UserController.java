package ru.example.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.example.demo.dto.UserRequestDto;
import ru.example.demo.dto.UserRequestUpdateDto;
import ru.example.demo.dto.UserResponseDto;
import ru.example.demo.exceptions.EntityNotFoundException;
import ru.example.demo.exceptions.UserErrorResponse;
import ru.example.demo.exceptions.UserNotCreatedException;
import ru.example.demo.exceptions.UserNotUpdateException;
import ru.example.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getPeople")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getPeople() {
        return userService.findAll();
    }

    @GetMapping("/getUserById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserById(@PathVariable(value = "id", required = false) Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@RequestBody @Valid UserRequestDto userRequestDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMassage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errMassage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new UserNotCreatedException(errMassage.toString());
        }
        return userService.createUser(userRequestDto);
    }


    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam(value = "id", required = false) Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/update")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserRequestUpdateDto updateDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMassage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errMassage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new UserNotUpdateException(errMassage.toString());
        }
        userService.updateUser(updateDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
