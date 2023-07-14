package uz.spiders.ecommerce.service;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.spiders.ecommerce.entity.User;
import uz.spiders.ecommerce.entity.enums.RoleType;
import uz.spiders.ecommerce.exception.EmailNotVerifiedException;
import uz.spiders.ecommerce.exception.EmailSendingException;
import uz.spiders.ecommerce.exception.TokenExpiredOrInvalid;
import uz.spiders.ecommerce.exception.UsernameOrEmailAlreadyExists;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.AuthDTO;
import uz.spiders.ecommerce.payload.RegisterDTO;
import uz.spiders.ecommerce.payload.TokenDTO;
import uz.spiders.ecommerce.repository.RoleRepository;
import uz.spiders.ecommerce.repository.UserRepository;
import uz.spiders.ecommerce.security.JwtService;
import uz.spiders.ecommerce.service.interfaces.AuthService;
import uz.spiders.ecommerce.utils.Constants;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;

    @Override
    public ApiResponse<?> register(RegisterDTO registerDTO) {
        /*if (userRepository.existsByUsername(registerDTO.username()))
            throw new UsernameOrEmailAlreadyExists("Username is already registered");*/
        if (userRepository.existsByEmail(registerDTO.email()))
            throw new UsernameOrEmailAlreadyExists("Email is already registered");

        User regUser = new User(
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password()),
                roleRepository.findByRoleType(RoleType.USER).orElseThrow()
        );

        userRepository.save(regUser);
        //todo send email activation link to user
        CompletableFuture.runAsync(() -> {
            try {
                emailSenderService.sendEmail(regUser.getEmail(),
                        jwtService.generateVerificationToken(regUser));
            } catch (MessagingException e) {
                throw new EmailSendingException("Some error happened. Please try again later");
            }
        });

        return ApiResponse
                .successResponse(
                        TokenDTO.builder()
                                .accessToken(jwtService.generateAccessToken(regUser))
                                .refreshToken(jwtService.generateRefreshToken(regUser))
                                .build()
                );
    }

    @Override
    public ApiResponse<?> authenticate(AuthDTO authDTO) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDTO.username(),
                        authDTO.password()));


        User principalUser = (User) authenticate.getPrincipal();
        if(!principalUser.isEnabled()){
            CompletableFuture.runAsync(() -> {
                try {
                    emailSenderService.sendEmail(principalUser.getEmail(),
                            jwtService.generateVerificationToken(principalUser));

                    throw new EmailNotVerifiedException();
                } catch (MessagingException e) {
                    throw new EmailSendingException("Some error happened. Please try again later");
                }
            });
        }

        return ApiResponse
                .successResponse(
                        new TokenDTO(
                                jwtService.generateAccessToken(principalUser),
                                jwtService.generateRefreshToken(principalUser),
                                Constants.AUTH_TYPE_BEARER));
    }

    @Override
    public ApiResponse<?> verification(String email, String token) {
        try {
            if (jwtService.isVerificationTokenValid(email, token)) {
                User user = userRepository.findByEmail(email).orElseThrow(
                        () -> new TokenExpiredOrInvalid("expired token"));

                user.setActive(true);
                userRepository.save(user);
            }
        }catch (Exception e){
            throw new TokenExpiredOrInvalid("expired token");
        }
        return ApiResponse
                .successResponse("Email successfully verified");
    }
}
