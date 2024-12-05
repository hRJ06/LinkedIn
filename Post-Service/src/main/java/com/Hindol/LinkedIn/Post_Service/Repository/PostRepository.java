package com.Hindol.LinkedIn.Post_Service.Repository;

import com.Hindol.LinkedIn.Post_Service.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}