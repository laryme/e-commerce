package uz.spiders.ecommerce.controller.interfaces;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.UserDTO;
import uz.spiders.ecommerce.utils.Constants;

@RequestMapping(UserController.BASE_PATH)
public interface UserController {
    String BASE_PATH = Constants.BASE_PATH + "/user";

    @GetMapping("/all")
    ApiResponse<?> getAllUsers(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id,asc") String[] sort);

    @GetMapping("/{id}")
    ApiResponse<?> getUserById(@PathVariable Integer id);

    @PutMapping("/{id}")
    ApiResponse<?> editUser(@PathVariable Integer id, @RequestBody UserDTO userDTO);

    @DeleteMapping("/{id}")
    ApiResponse<?> deleteUser(@PathVariable Integer id);

    @GetMapping("/user-block/{id}")
    ApiResponse<?> blockUser(@PathVariable Integer id);

    @PutMapping("/{id}/promote")
    ApiResponse<?> promoteUserToNewRole(@PathVariable Integer id, @RequestParam Integer roleId);

    @PostMapping("/{id}/upload-avatar")
    ApiResponse<?> changeUserAvatar(@PathVariable Integer id, @RequestParam("file") MultipartFile file);
}
