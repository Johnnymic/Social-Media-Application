package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower,Long> {
    @Query("SELECT f FROM Follower f WHERE f.user.id = :userId")
    List<Follower> findFollowersByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Follower f WHERE f.follower.id = :userId")
    List<Follower> findFollowingByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Follower f WHERE f.user.id = :userId AND f.follower.id = :followerId")
    Follower findFollower(@Param("userId") Long userId, @Param("followerId") Long followerId);


}
