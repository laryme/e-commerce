package uz.spiders.ecommerce.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.spiders.ecommerce.entity.Role;
import uz.spiders.ecommerce.entity.enums.Permission;
import uz.spiders.ecommerce.entity.enums.RoleType;
import uz.spiders.ecommerce.exception.DataNotFoundException;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.RoleDTO;
import uz.spiders.ecommerce.repository.RoleRepository;
import uz.spiders.ecommerce.service.interfaces.RoleService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public ApiResponse<List<Role>> getAllRoles() {
        return ApiResponse
                .successResponse(roleRepository.findAll());
    }

    @Override
    public ApiResponse<Role> getRoleById(Integer id) {
        return ApiResponse
                .successResponse(roleRepository.findById(id)
                        .orElseThrow(() -> new DataNotFoundException("Role not found with given id")));
    }

    @Override
    public ApiResponse<?> createNewRole(RoleDTO roleDTO) {
        roleRepository.save(roleMapper(roleDTO));
        return ApiResponse.successResponse("Role successfully created");
    }

    @Override
    public ApiResponse<?> deleteRoleById(Integer id) {
        roleRepository.deleteById(id);

        return ApiResponse
                .successResponse("Role successfully deleted");
    }

    @Override
    public ApiResponse<?> updateRole(Integer id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Role not found with given id"));

        role.setName(roleDTO.name());
        role.setPermissionSet(permissionMapper(roleDTO.permissions()));

        roleRepository.save(role);
        return ApiResponse
                .successResponse("Role successfully updated");
    }

    @Override
    public ApiResponse<Map<String, Integer>> getAllPermissions() {
        return ApiResponse
                .successResponse(
                        Arrays.stream(Permission.values())
                                .collect(Collectors.toMap(Enum::name, Enum::ordinal)));
    }

    private Role roleMapper(RoleDTO roleDTO) {
        return Role.builder()
                .name(roleDTO.name())
                .roleType(RoleType.OTHER)
                .permissionSet(permissionMapper(roleDTO.permissions()))
                .build();
    }

    private Set<Permission> permissionMapper(List<Integer> permissions) {
        return Arrays.stream(Permission.values())
                .filter(p -> permissions.contains(p.ordinal()))
                .collect(Collectors.toSet());
    }
}
