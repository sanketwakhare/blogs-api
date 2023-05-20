package com.sanket.blogsapi.services.tokens.sst;

import com.sanket.blogsapi.common.BaseEntity;
import com.sanket.blogsapi.users.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Entity(name = "user_tokens")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    private Date expiresAt;
}
