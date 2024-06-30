package ru.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

//    TestEntity findById(Long id);

}
