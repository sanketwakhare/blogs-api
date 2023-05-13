package com.sanket.blogsapi.users;

import com.sanket.blogsapi.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "users")
@Builder
@ToString
public class UserEntity extends BaseEntity {

    @NonNull
    @Column(name = "username", nullable = false, unique = true, length = 30)
    private String username;

    @NonNull
    @Column(name = "email", nullable = false, unique = true, length = 30)
    private String email;

    @NonNull
    @Setter
    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Setter
    @Column
    private String bio;

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "following")
//    @JoinTable(
//            name = "user_followers",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id")
//    )
//    @JoinTable(
//            name = "followers_followings",
//            joinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id")
//    )
    private List<UserEntity> followers;

    @ManyToMany
//    @JoinTable(
//            name = "user_following",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id")
//    )
    private List<UserEntity> following;
}
