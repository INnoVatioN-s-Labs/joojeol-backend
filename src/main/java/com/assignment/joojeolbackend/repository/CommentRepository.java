package com.assignment.joojeolbackend.repository;

import com.assignment.joojeolbackend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    
    // Fetch top-level comments for a post, including eager fetch for children if possible, 
    // but for simplicity just fetching roots and letting JPA handle children.
    // For MVP, N+1 layout might occur but we can optimize later.
    List<Comment> findByPostIdAndParentIsNullOrderByCreatedAtAsc(UUID postId);
}
