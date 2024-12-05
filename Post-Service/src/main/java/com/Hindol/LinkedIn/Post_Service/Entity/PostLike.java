package com.Hindol.LinkedIn.Post_Service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post_likes")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long postId;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
