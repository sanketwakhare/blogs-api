package com.sanket.blogsapi.roles;

import com.sanket.blogsapi.common.dtos.ResponseDTO;
import com.sanket.blogsapi.roles.constants.RolesSuccessMessages;
import com.sanket.blogsapi.roles.dtos.AssignOrRevokeRoleRequestDTO;
import com.sanket.blogsapi.roles.dtos.CreateOrDeleteRoleRequestDTO;
import com.sanket.blogsapi.roles.exceptions.RoleAlreadyAssignedException;
import com.sanket.blogsapi.roles.exceptions.RoleAlreadyExistsException;
import com.sanket.blogsapi.roles.exceptions.RoleNotFoundException;
import com.sanket.blogsapi.roles.exceptions.UserRoleNotPresentException;
import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
// allow only admins to access this controller
@PreAuthorize("hasRole('ADMIN')")
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
//    this is not required as we have added @PreAuthorize("hasRole('ADMIN')") at class level
//    // allow only admins to create a role
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRole(@RequestBody CreateOrDeleteRoleRequestDTO requestDTO) {
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
//    this is not required as we have added @PreAuthorize("hasRole('ADMIN')") at class level
//    // allow only admins to assign a role
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> assignRole(@RequestBody AssignOrRevokeRoleRequestDTO requestDTO) {
        UserEntity userEntity = rolesService.assignRole(requestDTO.getUsername(), requestDTO.getRole());
        ResponseDTO responseDTO = new ResponseDTO(String.format(RolesSuccessMessages.ROLE_ASSIGNED, requestDTO.getRole().name(), userEntity.getUsername()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
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
//    this is not required as we have added @PreAuthorize("hasRole('ADMIN')") at class level
//    // allow only admins to revoke a role
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> revokeRole(@RequestBody AssignOrRevokeRoleRequestDTO requestDTO) {
        UserEntity userEntity = rolesService.revokeRole(requestDTO.getUsername(), requestDTO.getRole());
        ResponseDTO responseDTO = new ResponseDTO(String.format(RolesSuccessMessages.ROLE_REVOKED, requestDTO.getRole().name(), userEntity.getUsername()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    /**
     * Deletes a role
     *
     * @param requestDTO role to be deleted
     * @return success message if role is deleted successfully
     * @throws RoleNotFoundException if role is not found
     */
    @DeleteMapping("")
//    this is not required as we have added @PreAuthorize("hasRole('ADMIN')") at class level
//    // allow only admins to revoke a role
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteRole(@RequestBody CreateOrDeleteRoleRequestDTO requestDTO) {
        rolesService.deleteRole(requestDTO.getRole());
        ResponseDTO responseDTO = new ResponseDTO(String.format(RolesSuccessMessages.ROLE_DELETED, requestDTO.getRole().name()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }
}
