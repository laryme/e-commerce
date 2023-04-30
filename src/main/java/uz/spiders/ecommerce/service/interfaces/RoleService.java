package uz.spiders.ecommerce.service.interfaces;


import uz.spiders.ecommerce.entity.Role;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.RoleDTO;

import java.util.List;
import java.util.Map;

public interface RoleService {
    ApiResult<List<Role>> getAllRoles();

    ApiResult<Role> getRoleById(Integer id);

    ApiResult<?> createNewRole(RoleDTO roleDTO);

    ApiResult<?> deleteRoleById(Integer id);

    ApiResult<?> updateRole(Integer id, RoleDTO roleDTO);

    ApiResult<Map<String, Integer>> getAllPermissions();
}
