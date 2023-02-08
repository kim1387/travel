package com.kim1387.travel.domain.user.domain.repository;

import com.kim1387.travel.domain.user.domain.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {

  @Query(value = "select u from Users u where u.id = :id and u.isActivated = true")
  Optional<Users> findUsersById(@Param("id") Long id);
}
