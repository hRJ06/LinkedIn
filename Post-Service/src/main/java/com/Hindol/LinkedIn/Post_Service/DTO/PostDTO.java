package com.Hindol.LinkedIn.Post_Service.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    public Long id;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
}
