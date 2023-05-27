package com.sanket.blogsapi.roles;

import com.sanket.blogsapi.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "roles")
public class RoleEntity extends BaseEntity {

    @NotNull
    @Column(name = "role", nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    private RolesEnum role;
}
