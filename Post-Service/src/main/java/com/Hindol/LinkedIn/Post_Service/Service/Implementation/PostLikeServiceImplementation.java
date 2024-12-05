package com.Hindol.LinkedIn.Post_Service.Service.Implementation;

import com.Hindol.LinkedIn.Post_Service.Entity.PostLike;
import com.Hindol.LinkedIn.Post_Service.Exception.BadRequestException;
import com.Hindol.LinkedIn.Post_Service.Exception.ResourceNotFoundException;
import com.Hindol.LinkedIn.Post_Service.Repository.PostLikeRepository;
import com.Hindol.LinkedIn.Post_Service.Repository.PostRepository;
import com.Hindol.LinkedIn.Post_Service.Service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeServiceImplementation implements PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Override
    public void likePost(Long postId, Long userId) {
        log.info("Attempting to like Post with ID : {}", postId);
        boolean exists = postRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("No Post found with ID : " + postId);
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(alreadyLiked) throw new BadRequestException("Unable to like same post again");
        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Post with ID : {}, liked successfully", postId);
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        log.info("Attempting to unlike Post with ID : {}", postId);
        boolean exists = postRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("No Post found with ID : " + postId);
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!alreadyLiked) throw new BadRequestException("Unable to unlike post not liked");
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        log.info("Post with ID : {}, unliked successfully", postId);
    }
}
