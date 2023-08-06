package com.michael.socialmedia.service.serviceImpl;

import com.michael.socialmedia.domain.Comment;
import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.CommentRequest;
import com.michael.socialmedia.dto.request.EditCommentRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.CommentResponse;
import com.michael.socialmedia.dto.response.EditCommentResponse;
import com.michael.socialmedia.exceptions.CommentNotFoundException;
import com.michael.socialmedia.exceptions.ResourceNotFoundException;
import com.michael.socialmedia.exceptions.UserNotAuthenticated;
import com.michael.socialmedia.repository.CommentRepository;
import com.michael.socialmedia.repository.PostRepository;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.service.CommentService;
import com.michael.socialmedia.utils.EmailUtils;
import com.michael.socialmedia.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private  final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Override
    public CommentResponse createNewComment(CommentRequest commentRequest) {
      User user = userRepository.findByEmail(EmailUtils.getEmailFromContent())
                .orElseThrow(()-> new UserNotAuthenticated("USER NOT AUTHENTICATED"));
      Post post = postRepository.findById(commentRequest.getPostId())
              .orElseThrow(()-> new CommentNotFoundException("comment not found"));
      Comment  comment = Mapper.mapToComment(post,commentRequest);
      comment.getPost().setUser(user);

      var newComment = commentRepository.save(comment);
      return Mapper.mapToCommentResponse(newComment);
    }

    @Override
    public CommentResponse viewComment(Long commentId) {
        Comment comment =commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("NOT FOUND"));
        return mapToCommentEntity(comment);
    }



    @Override
    public List<CommentResponse> viewAllComment() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList.stream().map(this::mapToCommentEntity).collect(Collectors.toList());
    }

    @Override
    public EditCommentResponse editComment(Long commentId, EditCommentRequest comment) {
        User user = userRepository.findByEmail(EmailUtils.getEmailFromContent())
                .orElseThrow(()-> new UserNotAuthenticated("USER NOT AUTHENTICATED"));
        Comment editComment =commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentNotFoundException("comment not found"));
        editComment.setContent(comment.getContent());
        editComment.getPost().setUser(user);
         commentRepository.save(editComment);
        return EditCommentResponse.builder()
                .comment(editComment.getContent())
                .build();
    }

    @Override
    public String deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("comment not found"));
        commentRepository.delete(comment);
        return "comment is successfully deleted";
    }

    @Override
    public Page<CommentResponse> viewCommentsByPagination(Integer pageNo, Integer pageSize, String sortBy) {
        List<Comment>commentList = commentRepository.findAll();
        List<CommentResponse> commentResponses = commentList.
                stream().map(comment -> CommentResponse.builder().comment(comment.getContent()).build()).collect(Collectors.toList());
        Collections.sort(commentResponses, Comparator.comparing(CommentResponse::getPostId,Collections.reverseOrder()));
        int min = pageNo*pageSize;
        int max = Math.min(pageSize *(pageNo +1), commentResponses.size());
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, Sort.Direction.ASC,sortBy);
        return new PageImpl<>(commentResponses.subList(min,max),pageRequest,commentResponses.size());
    }

    private CommentResponse mapToCommentEntity(Comment comment) {
        return CommentResponse.builder()
                .comment(comment.getContent())
                .build();

    }


}
