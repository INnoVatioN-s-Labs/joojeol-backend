package com.assignment.joojeolbackend.repository;

import com.assignment.joojeolbackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.postLikeList ORDER BY p.createdAt DESC")
    List<Post> findAllByOrderByCreatedAtDesc();
}
