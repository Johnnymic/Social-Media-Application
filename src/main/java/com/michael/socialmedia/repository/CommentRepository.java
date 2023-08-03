package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment,Long> {

}
