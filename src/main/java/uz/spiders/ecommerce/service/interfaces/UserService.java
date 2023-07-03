package uz.spiders.ecommerce.service.interfaces;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.UserDTO;

public interface UserService {
    ApiResult<?> getAllUsers(PageRequest pageRequest);

    ApiResult<?> getUserById(Integer id);

    ApiResult<?> editUser(Integer id, UserDTO userDTO);

    ApiResult<?> changeDeletedFieldStatus(Integer id);

    ApiResult<?> deleteUserById(Integer id);

    ApiResult<?> changeBlockStatus(Integer id);

    ApiResult<?> promoteUserToNewRole(Integer id, Integer roleId);

    ApiResult<?> changeUserAvatar(Integer id, MultipartFile file);
}
