package ru.example.demo.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.dto.UserRequestDto;
import ru.example.demo.dto.UserRequestUpdateDto;
import ru.example.demo.dto.UserResponseDto;
import ru.example.demo.entity.User;
import ru.example.demo.exceptions.EntityNotFoundException;
import ru.example.demo.exceptions.UserNotCreatedException;
import ru.example.demo.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Data
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override

    public UserResponseDto getUserById(Long id) {
        Optional<User> optionalTest = userRepository.findById(id);
        User user1 = optionalTest.orElseThrow(
                () -> {
                    log.error("Ошибка поиска id='{}'. В базе нет такой записи", id);
                    throw new EntityNotFoundException("Recorder with id= " + id + " not found");
                }
        );
        return fromEntity(user1);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> optionalTest = userRepository.findById(id);
        optionalTest.orElseThrow(
                () -> {
                    log.error("Ошибка поиска id='{}'. В базе нет такой записи", id);
                    throw new EntityNotFoundException("Recorder with id= " + id + " not found");
                }
        );
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Long createUser(UserRequestDto userRequestDto) {
        String name = userRequestDto.getName();
        String surname = userRequestDto.getSurname();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(surname)) {
            throw new UserNotCreatedException("Имя и фамилия должны быть заполнены");
        }
        User entity = toEntity(userRequestDto);
        User save = userRepository.save(entity);
        return save.getId();
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestUpdateDto updateDto) {
        Long id = updateDto.getId();
        Optional<User> optionalId = userRepository.findById(id);
        User user = optionalId.orElseThrow(
                () -> {
                    log.error("Ошибка поиска id='{}'. В базе нет такой записи", id);
                    throw new EntityNotFoundException("Recorder with id= " + id + " not found");
                }
        );
        user.setSurname(updateDto.getSurname());
        user.setName(updateDto.getName());
        User savedUser = userRepository.save(user);
        return fromEntity(savedUser);
    }

    private UserResponseDto fromEntity(User entity) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setName(entity.getName());
        responseDto.setSurname(entity.getSurname());
        return responseDto;
    }

    private User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        return user;
    }
}