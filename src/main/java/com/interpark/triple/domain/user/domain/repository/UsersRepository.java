package com.interpark.triple.domain.user.domain.repository;

import com.interpark.triple.domain.user.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findUserById(@Param("id") Long id);

}
