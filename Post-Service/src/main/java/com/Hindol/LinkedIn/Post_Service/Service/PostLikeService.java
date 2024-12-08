package com.Hindol.LinkedIn.Post_Service.Service;

public interface PostLikeService {
    void likePost(Long postId);
    void unlikePost(Long postId);
}
