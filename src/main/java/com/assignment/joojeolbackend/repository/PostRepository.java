package com.assignment.joojeolbackend.repository;

import com.assignment.joojeolbackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.postLikeList ORDER BY p.createdAt DESC")
    List<Post> findAllByOrderByCreatedAtDesc();

    @Query("""
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.postLikeList
            LEFT JOIN p.hashtags h
            WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(h) LIKE LOWER(CONCAT('%', :keyword, '%'))
            ORDER BY p.createdAt DESC
            """)
    List<Post> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.postLikeList WHERE p.id = :id")
    java.util.Optional<Post> findByIdWithLikes(UUID id);
}
