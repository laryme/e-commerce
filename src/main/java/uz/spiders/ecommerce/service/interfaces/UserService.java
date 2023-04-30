package uz.spiders.ecommerce.service.interfaces;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.UserDTO;

import java.util.UUID;

public interface UserService {
    ApiResult<?> getAllUsers(PageRequest pageRequest);

    ApiResult<?> getUserById(UUID id);

    ApiResult<?> editUser(UUID id, UserDTO userDTO);

    ApiResult<?> changeDeletedFieldStatus(UUID id);

    ApiResult<?> deleteUserById(UUID id);

    ApiResult<?> changeBlockStatus(UUID id);

    ApiResult<?> promoteUserToNewRole(UUID id, Integer roleId);

    ApiResult<?> changeUserAvatar(UUID id, MultipartFile file);
}
