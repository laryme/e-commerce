package uz.spiders.ecommerce.service.interfaces;


import uz.spiders.ecommerce.entity.Role;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.RoleDTO;

import java.util.List;
import java.util.Map;

public interface RoleService {
    ApiResponse<List<Role>> getAllRoles();

    ApiResponse<Role> getRoleById(Integer id);

    ApiResponse<?> createNewRole(RoleDTO roleDTO);

    ApiResponse<?> deleteRoleById(Integer id);

    ApiResponse<?> updateRole(Integer id, RoleDTO roleDTO);

    ApiResponse<Map<String, Integer>> getAllPermissions();
}
