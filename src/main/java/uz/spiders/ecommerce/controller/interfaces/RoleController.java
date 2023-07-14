package uz.spiders.ecommerce.controller.interfaces;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.spiders.ecommerce.entity.Role;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.RoleDTO;
import uz.spiders.ecommerce.utils.Constants;

import java.util.List;
import java.util.Map;

@RequestMapping(RoleController.BASE_PATH)
public interface RoleController {
    String BASE_PATH = Constants.BASE_PATH + "/role";

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_LIST')")
    ApiResponse<List<Role>> getAllRole();

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ONE')")
    ApiResponse<Role> getRoleById(@PathVariable Integer id);

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    ApiResponse<?> createNewRole(@RequestBody RoleDTO roleDTO);

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    ApiResponse<?> updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    ApiResponse<?> deleteRoleById(@PathVariable Integer id);

    @GetMapping("/permission/all")
    ApiResponse<Map<String, Integer>> getAllPermissions();

}
