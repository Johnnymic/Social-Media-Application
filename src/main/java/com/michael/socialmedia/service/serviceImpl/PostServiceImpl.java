package com.michael.socialmedia.service.serviceImpl;

import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.EditPostRequest;
import com.michael.socialmedia.dto.request.PostRequest;
import com.michael.socialmedia.dto.response.EditPostResponse;
import com.michael.socialmedia.dto.response.PostResponse;
import com.michael.socialmedia.exceptions.PostNotfoundException;
import com.michael.socialmedia.exceptions.ResourceNotFoundException;
import com.michael.socialmedia.exceptions.UserNotAuthenticated;
import com.michael.socialmedia.repository.PostRepository;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.service.PostService;
import com.michael.socialmedia.utils.EmailUtils;
import com.michael.socialmedia.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final  UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostResponse createPost(PostRequest postRequest) {

        User user = userRepository.findByEmail(EmailUtils.getEmailFromContent())
                .orElseThrow(() -> new UserNotAuthenticated("please login"));
        Post post = mapToPost(postRequest);
        var newPost = postRepository.save(post);
        return mapToPostResponse(newPost);
    }

    @Override
    public PostResponse viewPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post not found"));
        return mapToPostResponse(post);
    }

    @Override
    public List<PostResponse> viewAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }

    @Override
    public String deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found"));
        postRepository.delete(post);
        System.out.println(post);

        return "Post Successfully deleted";
    }


    @Override
    public Page<PostResponse> viewPaginatedPost(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Post> postList = postRepository.findAll();
        List<PostResponse> postResponses =
                postList.stream()
                        .map(post ->
                                PostResponse.builder()
                                .id(post.getId())
                                .content(post.getContent())
                                .build()).
                        collect(Collectors.toList());
        Collections.sort(postResponses, Comparator.comparing(PostResponse::getId,Collections.reverseOrder()));
        int min= pageNo*pageSize;
        int max = Math.min(pageSize * (pageNo+1),postResponses.size());
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC,sortBy);
        return new PageImpl<>(postResponses.subList(min,max),pageRequest,postResponses.size());
    }

    @Override
    public EditPostResponse editPost(Long postId, EditPostRequest editPostResponse) {
        User user = userRepository.findByEmail(EmailUtils.getEmailFromContent())
                .orElseThrow(() -> new UserNotAuthenticated("please login"));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotfoundException("post not found"));
        post.setContent(editPostResponse.getContent());
        post.setUser(user);
        postRepository.save(post);
        return Mapper.mapToEditPostResponse(post);
    }




    private PostResponse mapToPostResponse(Post newPost) {
        return  PostResponse.builder()
                .id(newPost.getId())
                .content(newPost.getContent())
                .build();
    }

    private Post mapToPost(PostRequest postRequest) {
        return Post.builder()
                .id(postRequest.getId())
                .content(postRequest.getContent())
                .build();
    }



}
