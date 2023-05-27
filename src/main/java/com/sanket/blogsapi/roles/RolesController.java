package com.sanket.blogsapi.roles;

import com.sanket.blogsapi.roles.constants.RolesSuccessMessages;
import com.sanket.blogsapi.roles.dtos.AssignRoleRequestDTO;
import com.sanket.blogsapi.roles.dtos.CreateRoleRequestDTO;
import com.sanket.blogsapi.roles.exceptions.RoleAlreadyExistsException;
import com.sanket.blogsapi.roles.exceptions.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesController {

    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    /**
     * Assigns a role to a user
     *
     * @param requestDTO username and role to be assigned
     * @return success message if role is assigned successfully
     * @throws RoleNotFoundException if role is not found
     */
    @PostMapping("/assign")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleRequestDTO requestDTO) {
        rolesService.assignRole(requestDTO.getUsername(), requestDTO.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format(RolesSuccessMessages.ROLE_ASSIGNED, requestDTO.getRole().name()));
    }

    /**
     * Creates a new role
     *
     * @param requestDTO role to be created
     * @return success message if role is created successfully
     * @throws RoleAlreadyExistsException if role already exists
     */
    @PostMapping("")
    public ResponseEntity<?> createRole(@RequestBody CreateRoleRequestDTO requestDTO) {
        RolesEnum role = requestDTO.getRole();
        RoleEntity roleEntity = rolesService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format(RolesSuccessMessages.ROLE_CREATED, roleEntity.getRole().name()));
    }
}
