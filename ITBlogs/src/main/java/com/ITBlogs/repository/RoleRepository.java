package com.ITBlogs.repository;

import com.ITBlogs.models.Enum.ERole;
import com.ITBlogs.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
