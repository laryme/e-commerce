package uz.spiders.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.controller.interfaces.UserController;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.UserDTO;
import uz.spiders.ecommerce.service.interfaces.UserService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;
    @Override
    public ApiResponse<?> getAllUsers(Integer page, int size, String[] sort) {
        return userService.getAllUsers(
                PageRequest.of(
                        page,
                        size,
                        Objects.equals(sort[1], "desc") ?
                                Sort.by(sort[0])
                                        .descending() :
                                Sort.by(sort[0])
                                        .ascending()));
    }

    @Override
    public ApiResponse<?> getUserById(Integer id) {
        return userService.getUserById(id);
    }

    @Override
    public ApiResponse<?> editUser(Integer id, UserDTO userDTO) {
        return userService.editUser(id, userDTO);
    }

    @Override
    public ApiResponse<?> deleteUser(Integer id) {
        return userService.deleteUserById(id);
    }

    @Override
    public ApiResponse<?> blockUser(Integer id) {
        return userService.changeBlockStatus(id);
    }

    @Override
    public ApiResponse<?> promoteUserToNewRole(Integer id, Integer roleId) {
        return userService.promoteUserToNewRole(id, roleId);
    }

    @Override
    public ApiResponse<?> changeUserAvatar(Integer id, MultipartFile file) {
        return userService.changeUserAvatar(id, file);
    }
}
