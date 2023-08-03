package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.Follow;
import com.michael.socialmedia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follow,Long> {
   Follow findByFollowerAndFollowing(User followerUsername, User followingUsername);


}
