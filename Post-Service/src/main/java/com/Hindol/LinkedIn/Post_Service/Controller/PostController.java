package com.Hindol.LinkedIn.Post_Service.Controller;

import com.Hindol.LinkedIn.Post_Service.DTO.PostCreateRequestDTO;
import com.Hindol.LinkedIn.Post_Service.DTO.PostDTO;
import com.Hindol.LinkedIn.Post_Service.Service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDTO postCreateRequestDTO, HttpServletRequest httpServletRequest) {
        PostDTO createdPost = postService.createPost(postCreateRequestDTO, 1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }
}
