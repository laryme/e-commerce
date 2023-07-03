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
import uz.spiders.ecommerce.payload.ApiResult;
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
    public ApiResult<Page<User>> getAllUsers(PageRequest pageRequest) {
        return ApiResult
                .successResponse(userRepository.findAll(pageRequest));
    }

    @Override
    public ApiResult<?> getUserById(Integer id) {
        return ApiResult
                .successResponse(
                        userRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("User not found with given id")));
    }

    @Override
    public ApiResult<?> editUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        userRepository.save(userMapper(user, userDTO));
        return ApiResult
                .successResponse("User successfully updated");
    }

    @Override
    public ApiResult<?> changeDeletedFieldStatus(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        boolean nonDeleted = user.isNonDeleted();

        user.setNonDeleted(!user.isNonDeleted());
        userRepository.save(user);

        if(nonDeleted){
            return ApiResult
                    .successResponse("User successfully deleted");
        }

        return ApiResult
                .successResponse("User successfully restored");
    }

    //for delete
    @Override
    public ApiResult<?> deleteUserById(Integer id) {
        userRepository.deleteById(id);
        return ApiResult
                .successResponse("User successfully deleted");
    }

    @Override
    public ApiResult<?> changeBlockStatus(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        boolean flag = user.isNonBlocked();

        user.setNonBlocked(!user.isNonBlocked());
        userRepository.save(user);

        if(flag){
            return ApiResult
                    .successResponse("User successfully blocked");
        }
        return ApiResult
                .successResponse("User successfully unblocked");
    }

    @Override
    public ApiResult<?> promoteUserToNewRole(Integer id, Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new DataNotFoundException("Role not found with given id"));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        user.setRoles(role);

        userRepository.save(user);

        return ApiResult
                .successResponse("User successfully promoted");
    }

    @Override
    public ApiResult<?> changeUserAvatar(Integer id, MultipartFile file) {
        /*User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with given id"));

        String photoUrl = "https://" + s3BucketName + endpointUrl+id;
        s3StorageService.save(id.toString(), file);

        user.setAvatarUrl(photoUrl);

        userRepository.save(user);*/
        return ApiResult
                .successResponse("User profile photo successfully changed");
    }

    private User userMapper(User user, UserDTO userDTO) {
        user.setFullName(userDTO.fullName());
        user.setPassword(userDTO.password());
        user.setEmail(user.getEmail());
        return user;
    }
}
