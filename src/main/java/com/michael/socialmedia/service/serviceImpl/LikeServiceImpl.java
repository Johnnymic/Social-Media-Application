package com.michael.socialmedia.service.serviceImpl;

import com.michael.socialmedia.domain.Like;
import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.exceptions.PostNotfoundException;
import com.michael.socialmedia.exceptions.UserNotAuthenticated;
import com.michael.socialmedia.repository.LikeRepository;
import com.michael.socialmedia.repository.PostRepository;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private  final UserRepository userRepository;

    private  final PostRepository postRepository;

    private final LikeRepository likeRepository;


    @Override
    public String likePost(Long postId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotAuthenticated("User not logged in"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotfoundException("Post not found"));

        Like like = likeRepository.findByUserAndPost(user,post);

        if(like !=null){
            unlikePost(like,post);
        }
        else {
            likedPost(user,post);
        }

        return null;
    }

    private void likedPost(User user, Post post) {
        Like newLike = new Like();
        newLike.setPost(post);
        newLike.setUser(user);
        post.setLikeCount(post.getLikeCount() + 1);
        likeRepository.save(newLike);


    }

    private void unlikePost(Like like, Post post) {
        likeRepository.delete(like);
        post.setLikeCount(post.getLikeCount() -1);


    }


}
