package com.sanket.blogsapi.users;

import com.sanket.blogsapi.common.BaseEntity;
import com.sanket.blogsapi.common.constants.CommonConstants;
import com.sanket.blogsapi.roles.RoleEntity;
import com.sanket.blogsapi.users.constants.UsersErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "users")
@Builder
public class UserEntity extends BaseEntity {

    @NonNull
    @Size(min = 3, max = 30,
            message = UsersErrorMessages.USERNAME_SIZE_CONSTRAINT_VIOLATION_ERROR)
    @Column(name = "username", nullable = false, unique = true, length = 30)
    private String username;

    @NonNull
    @Email(regexp = CommonConstants.EMAIL_VALIDATION_PATTERN,
            message = UsersErrorMessages.INVALID_USER_EMAIL)
    @Column(name = "email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "password")
    private String password;

    @Column
    private String bio;

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "following")
    private Set<UserEntity> followers;

    @ManyToMany
    private Set<UserEntity> following;

//    if two separate mapping tables are needed for followers and following, then use the below code
    /* @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id")
    )
    private List<UserEntity> followers;

    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id")
    )
    private List<UserEntity> following; */

    @ManyToMany
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private Set<RoleEntity> roles;

    @Column(name = "auth_provider", nullable = false)
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private AuthProvider authProvider;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;
}
