package com.Hindol.LinkedIn.User_Service.DTO;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private String name;
    private String email;
    private String password;
}
