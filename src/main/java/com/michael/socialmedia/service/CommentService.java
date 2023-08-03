package com.michael.socialmedia.service;

import com.michael.socialmedia.dto.request.CommentRequest;
import com.michael.socialmedia.dto.request.EditCommentRequest;
import com.michael.socialmedia.dto.response.CommentResponse;
import com.michael.socialmedia.dto.response.EditCommentResponse;
import org.springframework.data.domain.Page;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface CommentService {
    CommentResponse createNewComment(CommentRequest comment);

    CommentResponse viewComment(Long commentId);

    List<CommentResponse> viewAllComment();

    EditCommentResponse editComment(Long commentId, EditCommentRequest comment);

    String deleteComment(Long commentId);

    Page<CommentResponse> viewCommentsByPagination(Integer pageNo, Integer pageSize, String sortBy);
}
