package com.Hindol.LinkedIn.User_Service.Service.Implementation;

import com.Hindol.LinkedIn.User_Service.DTO.LoginRequestDTO;
import com.Hindol.LinkedIn.User_Service.DTO.SignUpRequestDTO;
import com.Hindol.LinkedIn.User_Service.DTO.UserDTO;
import com.Hindol.LinkedIn.User_Service.Entity.User;
import com.Hindol.LinkedIn.User_Service.Exception.BadRequestException;
import com.Hindol.LinkedIn.User_Service.Exception.ResourceNotFoundException;
import com.Hindol.LinkedIn.User_Service.Repository.UserRepository;
import com.Hindol.LinkedIn.User_Service.Service.AuthService;
import com.Hindol.LinkedIn.User_Service.Util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTServiceImplementation jwtServiceImplementation;
    @Override
    public UserDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        boolean exists = userRepository.existsByEmail(signUpRequestDTO.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists, cannot signup again");
        }
        User user = modelMapper.map(signUpRequestDTO, User.class);
        user.setPassword(PasswordUtil.hashPassword(signUpRequestDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("No User found with Email : " + email));
        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDTO.getPassword(), user.getPassword());
        if(!isPasswordMatch) {
            throw new BadRequestException("Incorrect Password");
        }
        return jwtServiceImplementation.generateAccessToken(user);
    }
}
