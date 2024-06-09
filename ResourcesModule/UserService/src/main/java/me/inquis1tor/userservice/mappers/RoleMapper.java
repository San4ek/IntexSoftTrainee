package me.inquis1tor.userservice.mappers;

import me.inquis1tor.userservice.DTOs.RoleDto;
import me.inquis1tor.userservice.entities.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleDto roleToDto(Role role);
    Role dtoToRole(RoleDto roleDto);
}
