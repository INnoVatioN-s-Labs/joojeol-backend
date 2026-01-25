package com.assignment.joojeolbackend.repository;

import com.assignment.joojeolbackend.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostLikeRepository extends JpaRepository<PostLike, UUID> {
}
