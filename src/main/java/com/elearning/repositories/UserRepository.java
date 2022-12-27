package com.elearning.repositories;

import com.elearning.model.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_SUPERVISOR'")
    List<User> getSupervisors();

    @Query("SELECT u FROM User u WHERE u.isSupervisedBy = :supervisorId")
    List<User> getUsersSupervisedBy(@Param("supervisorId") Long supervisorId);
}
