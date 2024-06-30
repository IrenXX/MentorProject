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
import ru.example.demo.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
@Data
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        Optional<User> optionalTest = userRepository.findById(id);
        if (optionalTest.isEmpty()) {
            log.error("Ошибка поиска id='{}'. В базе нет такой записи", id);
            throw new EntityNotFoundException("Запись не найдена с id=" + id);
        }

        User user = optionalTest.get();

        User user1 = optionalTest.orElseThrow(
                () -> new EntityNotFoundException("Запись не найдена с id=" + id)
        );

        return fromEntity(user1);
    }

    @Override
    @Transactional
    public Long createUser(UserRequestDto userRequestDto) {
        String name = userRequestDto.getName();
        String surname = userRequestDto.getSurname();
        if (StringUtils.isBlank(name) && StringUtils.isBlank(surname)) {
            throw new RuntimeException("Имя и фамилия должны быть заполнены");
        }
        User entity = toEntity(userRequestDto);
        User save = userRepository.save(entity);
        return save.getId();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        //всякие проверки
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestUpdateDto updateDto) {
        Long id = updateDto.getId();
        //всякие проверки
        Optional<User> optionalId = userRepository.findById(id);
        User user = optionalId.orElseThrow(
                () -> new EntityNotFoundException("Запись не найдена с id=" + id)
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