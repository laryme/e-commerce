package uz.spiders.ecommerce.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.entity.Role;
import uz.spiders.ecommerce.entity.User;
import uz.spiders.ecommerce.exception.DataNotFoundException;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.UserDTO;
import uz.spiders.ecommerce.repository.RoleRepository;
import uz.spiders.ecommerce.repository.UserRepository;
import uz.spiders.ecommerce.service.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final S3StorageService s3StorageService;

    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    @Value("${aws.s3.endpointUrl}")
    private String endpointUrl;
    @Override
    public ApiResponse<Page<User>> getAllUsers(PageRequest pageRequest) {
        return ApiResponse
                .successResponse(userRepository.findAll(pageRequest));
    }

    @Override
    public ApiResponse<?> getUserById(Integer id) {
        return ApiResponse
                .successResponse(
                        userRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("User not found with given id")));
    }

    @Override
    public ApiResponse<?> editUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        userRepository.save(userMapper(user, userDTO));
        return ApiResponse
                .successResponse("User successfully updated");
    }

    @Override
    public ApiResponse<?> changeDeletedFieldStatus(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        boolean nonDeleted = user.isNonDeleted();

        user.setNonDeleted(!user.isNonDeleted());
        userRepository.save(user);

        if(nonDeleted){
            return ApiResponse
                    .successResponse("User successfully deleted");
        }

        return ApiResponse
                .successResponse("User successfully restored");
    }

    //for delete
    @Override
    public ApiResponse<?> deleteUserById(Integer id) {
        userRepository.deleteById(id);
        return ApiResponse
                .successResponse("User successfully deleted");
    }

    @Override
    public ApiResponse<?> changeBlockStatus(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        boolean flag = user.isNonBlocked();

        user.setNonBlocked(!user.isNonBlocked());
        userRepository.save(user);

        if(flag){
            return ApiResponse
                    .successResponse("User successfully blocked");
        }
        return ApiResponse
                .successResponse("User successfully unblocked");
    }

    @Override
    public ApiResponse<?> promoteUserToNewRole(Integer id, Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new DataNotFoundException("Role not found with given id"));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        user.setRoles(role);

        userRepository.save(user);

        return ApiResponse
                .successResponse("User successfully promoted");
    }

    @Override
    public ApiResponse<?> changeUserAvatar(Integer id, MultipartFile file) {
        /*User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        String photoUrl = "https://" + s3BucketName + endpointUrl+id;
        s3StorageService.save(id.toString(), file);

        user.setAvatarUrl(photoUrl);

        userRepository.save(user);*/
        return ApiResponse
                .successResponse("User profile photo successfully changed");
    }

    private User userMapper(User user, UserDTO userDTO) {
        user.setFullName(userDTO.fullName());
        user.setPassword(userDTO.password());
        user.setEmail(user.getEmail());
        return user;
    }
}
