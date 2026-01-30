package com.assignment.joojeolbackend.config;

import com.assignment.joojeolbackend.domain.Comment;
import com.assignment.joojeolbackend.domain.Post;
import com.assignment.joojeolbackend.repository.CommentRepository;
import com.assignment.joojeolbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Profile("dev") // Only load data in dev profile
public class DataInitializer {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (postRepository.count() > 0) {
                System.out.println("Data already exists. Skipping initialization.");
                return;
            }

            System.out.println("Initializing Dummy Data...");
            createDummyData();
            System.out.println("Dummy Data Initialized.");
        };
    }

    @Transactional
    public void createDummyData() {
        // --- Post 1 ---
        Post post1 = Post.builder()
                .content("오늘 면접 망친 것 같아... 위로 좀 해줘.")
                .hashtags(Arrays.asList("취준생", "면접후기", "위로해줘", "화이팅", "우울해"))
                .build();
        postRepository.save(post1);

        Comment c1 = Comment.builder()
                .content("저도 오늘 면접 봤는데 실수 투성이였어요 ㅠㅠ 힘내요 우리!")
                .author("지나가던 취준생")
                .isMe(false)
                .post(post1)
                .build();
        commentRepository.save(c1);

        Comment r1 = Comment.builder()
                .content("감사해요.. 같이 합격했으면 좋겠네요 😢")
                .author("글쓴이")
                .isMe(true)
                .post(post1)
                .parent(c1)
                .build();
        commentRepository.save(r1);
        
        Comment c2 = Comment.builder()
                .content("면접관이 인재를 못 알아본 걸 수도 있어요. 맛있는 거 드시고 털어버리세요!")
                .author("응원단장")
                .isMe(false)
                .post(post1)
                .build();
        commentRepository.save(c2);


        // --- Post 2 ---
        Post post2 = Post.builder()
                .content("토스 미니앱 개발하는 거 생각보다 재밌는데? 특히 디자인 시스템 잡을 때가 제일 짜릿해.")
                .hashtags(Arrays.asList("개발자", "미니앱", "코딩공부", "사이드프로젝트", "UIUX"))
                .build();
        postRepository.save(post2);

        Comment c3 = Comment.builder()
                .content("TDS 컬러감 진짜 예쁘죠 ㅎㅎ 저도 참고 많이 하고 있어요.")
                .author("프론트엔드")
                .isMe(false)
                .post(post2)
                .build();
        commentRepository.save(c3);

        // --- Post 3 ---
        Post post3 = Post.builder()
                .content("오늘 점심 메뉴 추천받아요! 🍔")
                .imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=800&q=80")
                .hashtags(Arrays.asList("점메추", "직장인점심", "배고파", "맛집추천", "오늘뭐먹지"))
                .build();
        postRepository.save(post3);

        Comment c4 = Comment.builder()
                .content("사진 보니까 버거 땡기네요.. 오늘 점심은 햄버거다!")
                .author("버거킹")
                .isMe(false)
                .post(post3)
                .build();
        commentRepository.save(c4);

        Comment c5 = Comment.builder()
                .content("든든하게 국밥 어떠세요? 순대국밥 ㄱㄱ")
                .author("한식파")
                .isMe(false)
                .post(post3)
                .build();
        commentRepository.save(c5);


        // --- Post 4 ---
        Post post4 = Post.builder()
                .content("익명이라서 하는 말인데, 사실 나 요즘 좀 외로워.")
                .build();
        postRepository.save(post4);
    }
}
