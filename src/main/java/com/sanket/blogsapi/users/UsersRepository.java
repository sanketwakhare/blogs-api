package com.sanket.blogsapi.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    @Query("SELECT u.following FROM users u WHERE u.id = :userId")
    Set<UserEntity> getFollowingsForUserId(UUID userId);

    @Query("SELECT u.followers FROM users u WHERE u.id = :userId")
    Set<UserEntity> getFollowersForUserId(UUID userId);
}
