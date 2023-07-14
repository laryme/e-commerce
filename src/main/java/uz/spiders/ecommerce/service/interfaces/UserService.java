package uz.spiders.ecommerce.service.interfaces;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.UserDTO;

public interface UserService {
    ApiResponse<?> getAllUsers(PageRequest pageRequest);

    ApiResponse<?> getUserById(Integer id);

    ApiResponse<?> editUser(Integer id, UserDTO userDTO);

    ApiResponse<?> changeDeletedFieldStatus(Integer id);

    ApiResponse<?> deleteUserById(Integer id);

    ApiResponse<?> changeBlockStatus(Integer id);

    ApiResponse<?> promoteUserToNewRole(Integer id, Integer roleId);

    ApiResponse<?> changeUserAvatar(Integer id, MultipartFile file);
}
