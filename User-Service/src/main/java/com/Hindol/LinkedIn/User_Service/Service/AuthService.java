package com.Hindol.LinkedIn.User_Service.Service;

import com.Hindol.LinkedIn.User_Service.DTO.LoginRequestDTO;
import com.Hindol.LinkedIn.User_Service.DTO.SignUpRequestDTO;
import com.Hindol.LinkedIn.User_Service.DTO.UserDTO;

public interface AuthService {
    UserDTO signUp(SignUpRequestDTO signUpRequestDTO);
    String login(LoginRequestDTO loginRequestDTO);
}
