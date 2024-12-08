package com.Hindol.LinkedIn.Post_Service.Service.Implementation;

import com.Hindol.LinkedIn.Post_Service.Auth.UserContextHolder;
import com.Hindol.LinkedIn.Post_Service.Entity.Post;
import com.Hindol.LinkedIn.Post_Service.Entity.PostLike;
import com.Hindol.LinkedIn.Post_Service.Event.PostLikedEvent;
import com.Hindol.LinkedIn.Post_Service.Exception.BadRequestException;
import com.Hindol.LinkedIn.Post_Service.Exception.ResourceNotFoundException;
import com.Hindol.LinkedIn.Post_Service.Repository.PostLikeRepository;
import com.Hindol.LinkedIn.Post_Service.Repository.PostRepository;
import com.Hindol.LinkedIn.Post_Service.Service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeServiceImplementation implements PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final KafkaTemplate<Long, PostLikedEvent> kafkaTemplate;
    @Override
    public void likePost(Long postId) {
        log.info("Attempting to like Post with ID : {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("No Post found with ID : " + postId));
        Long userId = UserContextHolder.getCurrentUserId();
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(alreadyLiked) throw new BadRequestException("Unable to like same post again");
        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Post with ID : {}, liked successfully", postId);
        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                .postId(postId)
                .likedByUserId(userId)
                .creatorId(post.getUserId())
                .build();
        kafkaTemplate.send("post-liked-topic", postId, postLikedEvent);
    }

    @Override
    public void unlikePost(Long postId) {
        log.info("Attempting to unlike Post with ID : {}", postId);
        boolean exists = postRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("No Post found with ID : " + postId);
        Long userId = UserContextHolder.getCurrentUserId();
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!alreadyLiked) throw new BadRequestException("Unable to unlike post not liked");
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        log.info("Post with ID : {}, unliked successfully", postId);
    }
}
