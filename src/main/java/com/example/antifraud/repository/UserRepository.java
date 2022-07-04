package com.example.antifraud.repository;

import com.example.antifraud.model.UserModel;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends CrudRepository<UserModel, Long> {
    UserModel findUserByUsername(String username);

    Optional<UserModel> findUserEntityByUsernameIgnoreCase(String username);


    List<UserModel> findAll();

}
