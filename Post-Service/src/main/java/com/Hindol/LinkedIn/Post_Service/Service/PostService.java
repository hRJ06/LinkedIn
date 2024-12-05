package com.Hindol.LinkedIn.Post_Service.Service;

import com.Hindol.LinkedIn.Post_Service.DTO.PostCreateRequestDTO;
import com.Hindol.LinkedIn.Post_Service.DTO.PostDTO;

public interface PostService {
    PostDTO createPost(PostCreateRequestDTO postCreateRequestDTO, Long userId);
    PostDTO getPostById(Long postId);
}
