package uz.spiders.ecommerce.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.spiders.ecommerce.entity.Role;
import uz.spiders.ecommerce.entity.enums.Permission;
import uz.spiders.ecommerce.entity.enums.RoleType;
import uz.spiders.ecommerce.exception.DataNotFoundException;
import uz.spiders.ecommerce.payload.ApiResult;
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
    public ApiResult<List<Role>> getAllRoles() {
        return ApiResult
                .successResponse(roleRepository.findAll());
    }

    @Override
    public ApiResult<Role> getRoleById(Integer id) {
        return ApiResult
                .successResponse(roleRepository.findById(id)
                        .orElseThrow(() -> new DataNotFoundException("Role not found with given id")));
    }

    @Override
    public ApiResult<?> createNewRole(RoleDTO roleDTO) {
        roleRepository.save(roleMapper(roleDTO));
        return ApiResult.successResponse("Role successfully created");
    }

    @Override
    public ApiResult<?> deleteRoleById(Integer id) {
        roleRepository.deleteById(id);

        return ApiResult
                .successResponse("Role successfully deleted");
    }

    @Override
    public ApiResult<?> updateRole(Integer id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Role not found with given id"));

        role.setName(roleDTO.name());
        role.setPermissionSet(permissionMapper(roleDTO.permissions()));

        roleRepository.save(role);
        return ApiResult
                .successResponse("Role successfully updated");
    }

    @Override
    public ApiResult<Map<String, Integer>> getAllPermissions() {
        return ApiResult
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
