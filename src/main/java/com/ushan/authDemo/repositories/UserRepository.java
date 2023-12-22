package com.ushan.authDemo.repositories;

import com.ushan.authDemo.domains.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String username);

}
