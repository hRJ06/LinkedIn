package com.Hindol.LinkedIn.Post_Service.Service;

import com.Hindol.LinkedIn.Post_Service.DTO.PostCreateRequestDTO;
import com.Hindol.LinkedIn.Post_Service.DTO.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostCreateRequestDTO postCreateRequestDTO);
    PostDTO getPostById(Long postId);
    List<PostDTO> getAllPostsOfUser(Long userId);
}
