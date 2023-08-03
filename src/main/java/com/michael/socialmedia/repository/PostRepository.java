package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
