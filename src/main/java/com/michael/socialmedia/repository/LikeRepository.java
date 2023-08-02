package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.Like;
import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Like findByUserAndPost(User user, Post post);
 boolean existsByUserAndPost(User user, Post post);

}
