package com.sanket.blogsapi.roles;

import com.sanket.blogsapi.roles.exceptions.RoleAlreadyExistsException;
import com.sanket.blogsapi.roles.exceptions.RoleNotFoundException;
import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UsersRepository;
import com.sanket.blogsapi.users.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RolesService {

    private final RolesRepository rolesRepository;
    private final UsersRepository usersRepository;

    public RolesService(RolesRepository rolesRepository, UsersRepository usersRepository) {
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * Creates a new role
     *
     * @param role role to be created
     * @return created role
     * @throws RoleAlreadyExistsException if role already exists
     */
    public RoleEntity createRole(RolesEnum role) {
        Optional<RoleEntity> roleEntity = rolesRepository.findByRoleName(role);
        if (roleEntity.isPresent()) {
            throw new RoleAlreadyExistsException(role.name());
        }
        RoleEntity newRoleEntity = new RoleEntity();
        newRoleEntity.setRole(role);
        return rolesRepository.save(newRoleEntity);
    }

    /**
     * Returns a role by name
     *
     * @param role role name
     * @return role
     * @throws RoleNotFoundException if role is not found
     */
    public RoleEntity getRoleByName(RolesEnum role) {
        Optional<RoleEntity> roleEntity = rolesRepository.findByRoleName(role);
        if (roleEntity.isPresent()) {
            return roleEntity.get();
        }
        throw new RoleNotFoundException(role.name());
    }

    /**
     * Returns all roles for a user
     *
     * @param username username
     * @return set of roles
     * @throws UserNotFoundException if user is not found
     */
    public Set<RoleEntity> getAllRolesForUser(String username) {
        Optional<UserEntity> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        return user.get().getRoles();
    }

    /**
     * Assigns a role to a user
     *
     * @param username username
     * @param role     role to be assigned
     * @throws UserNotFoundException if user is not found
     * @throws RoleNotFoundException if role is not found
     */
    public void assignRole(String username, RolesEnum role) {
        Optional<UserEntity> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        RoleEntity roleEntity = getRoleByName(role);
        UserEntity userEntity = user.get();
        userEntity.getRoles().add(roleEntity);
        usersRepository.save(userEntity);
    }
}
