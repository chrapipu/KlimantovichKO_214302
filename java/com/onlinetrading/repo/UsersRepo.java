package com.onlinetrading.repo;

import com.onlinetrading.models.Users;
import com.onlinetrading.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    List<Users> findAllByOrderByRole();

    List<Users> findAllByRole(Role role);

    Users findByUsername(String username);

}
