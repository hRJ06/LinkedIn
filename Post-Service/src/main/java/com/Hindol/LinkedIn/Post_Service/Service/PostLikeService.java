package com.Hindol.LinkedIn.Post_Service.Service;

public interface PostLikeService {
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
}
