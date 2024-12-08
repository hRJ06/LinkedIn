package com.Hindol.LinkedIn.Post_Service.Service.Implementation;

import com.Hindol.LinkedIn.Post_Service.Auth.UserContextHolder;
import com.Hindol.LinkedIn.Post_Service.Client.ConnectionClient;
import com.Hindol.LinkedIn.Post_Service.DTO.PersonDTO;
import com.Hindol.LinkedIn.Post_Service.DTO.PostCreateRequestDTO;
import com.Hindol.LinkedIn.Post_Service.DTO.PostDTO;
import com.Hindol.LinkedIn.Post_Service.Entity.Post;
import com.Hindol.LinkedIn.Post_Service.Event.PostCreatedEvent;
import com.Hindol.LinkedIn.Post_Service.Exception.ResourceNotFoundException;
import com.Hindol.LinkedIn.Post_Service.Repository.PostRepository;
import com.Hindol.LinkedIn.Post_Service.Service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImplementation implements PostService {
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final ConnectionClient connectionClient;
    private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;
    @Override
    public PostDTO createPost(PostCreateRequestDTO postCreateRequestDTO) {
        Long userId = UserContextHolder.getCurrentUserId();
        Post post = modelMapper.map(postCreateRequestDTO, Post.class);
        post.setUserId(userId);
        Post savedPost = postRepository.save(post);
        PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                .postId(savedPost.getId())
                .creatorId(userId)
                .content(savedPost.getContent())
                .build();
        kafkaTemplate.send("post-created-topic", postCreatedEvent);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        log.debug("Retrieving Post with ID : {}", postId);
        List<PersonDTO> firstConnection = connectionClient.getFirstConnections();
        /* TODO: Send notification to connection */
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("No Post found with ID : " + postId));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPostsOfUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map((element) -> modelMapper.map(element, PostDTO.class)).collect(Collectors.toList());
    }
}
