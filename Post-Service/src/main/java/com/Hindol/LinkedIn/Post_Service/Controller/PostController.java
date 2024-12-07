package com.Hindol.LinkedIn.Post_Service.Controller;

import com.Hindol.LinkedIn.Post_Service.DTO.PostCreateRequestDTO;
import com.Hindol.LinkedIn.Post_Service.DTO.PostDTO;
import com.Hindol.LinkedIn.Post_Service.Service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDTO postCreateRequestDTO) {
        PostDTO createdPost = postService.createPost(postCreateRequestDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }
    @GetMapping("/user/{userId}/allPosts")
    public ResponseEntity<List<PostDTO>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }
}
