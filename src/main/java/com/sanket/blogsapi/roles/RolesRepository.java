package com.sanket.blogsapi.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, Long> {
    @Query("SELECT r FROM roles r WHERE r.role = ?1")
    Optional<RoleEntity> findByRoleName(RolesEnum role);
}
