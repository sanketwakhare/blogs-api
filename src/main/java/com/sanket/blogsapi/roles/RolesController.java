package com.sanket.blogsapi.roles;

import com.sanket.blogsapi.common.dtos.ResponseDTO;
import com.sanket.blogsapi.roles.constants.RolesSuccessMessages;
import com.sanket.blogsapi.roles.dtos.AssignOrRevokeRoleRequestDTO;
import com.sanket.blogsapi.roles.dtos.CreateRoleRequestDTO;
import com.sanket.blogsapi.roles.exceptions.RoleAlreadyAssignedException;
import com.sanket.blogsapi.roles.exceptions.RoleAlreadyExistsException;
import com.sanket.blogsapi.roles.exceptions.RoleNotFoundException;
import com.sanket.blogsapi.roles.exceptions.UserRoleNotPresentException;
import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.exceptions.UserNotFoundException;
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
        ResponseDTO responseDTO = new ResponseDTO(String.format(RolesSuccessMessages.ROLE_CREATED, roleEntity.getRole().name()));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Assigns a role to a user
     *
     * @param requestDTO username and role to be assigned
     * @return success message if role is assigned successfully
     * @throws RoleNotFoundException        if role is not found
     * @throws UserNotFoundException        if user is not found
     * @throws RoleAlreadyAssignedException if role is already assigned to user
     */
    @PostMapping("/assign")
    public ResponseEntity<ResponseDTO> assignRole(@RequestBody AssignOrRevokeRoleRequestDTO requestDTO) {
        UserEntity userEntity = rolesService.assignRole(requestDTO.getUsername(), requestDTO.getRole());
        ResponseDTO responseDTO = new ResponseDTO(String.format(RolesSuccessMessages.ROLE_ASSIGNED, requestDTO.getRole().name(), userEntity.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Revokes a role from a user
     *
     * @param requestDTO username and role to be revoked
     * @return success message if role is revoked successfully
     * @throws RoleNotFoundException       if role is not found
     * @throws UserNotFoundException       if user is not found
     * @throws UserRoleNotPresentException if role is not currently assigned to user
     */
    @PostMapping("/revoke")
    public ResponseEntity<ResponseDTO> revokeRole(@RequestBody AssignOrRevokeRoleRequestDTO requestDTO) {
        UserEntity userEntity = rolesService.revokeRole(requestDTO.getUsername(), requestDTO.getRole());
        ResponseDTO responseDTO = new ResponseDTO(String.format(RolesSuccessMessages.ROLE_REVOKED, requestDTO.getRole().name(), userEntity.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
