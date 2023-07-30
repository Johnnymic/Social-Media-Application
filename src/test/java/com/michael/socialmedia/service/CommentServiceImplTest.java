package com.michael.socialmedia.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.michael.socialmedia.domain.Comment;
import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.CommentRequest;
import com.michael.socialmedia.dto.request.EditCommentRequest;
import com.michael.socialmedia.dto.response.CommentResponse;
import com.michael.socialmedia.dto.response.EditCommentResponse;
import com.michael.socialmedia.exceptions.CommentNotFoundException;
import com.michael.socialmedia.exceptions.ResourceNotFoundException;
import com.michael.socialmedia.exceptions.UserNotAuthenticated;
import com.michael.socialmedia.repository.CommentRepository;
import com.michael.socialmedia.repository.PostRepository;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



// ...

//    @Test
//    public void createNewComment_ValidRequest_ReturnsCommentResponse() {
//        // Arrange
//        CommentRequest commentRequest = new CommentRequest();
//        commentRequest.setComment("Test comment");
//
//        // Mock the authentication context
//        Authentication authentication = mock(Authentication.class);
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        // Mock the user details
//        UserDetails userDetails = mock(UserDetails.class);
//        when(userDetails.getUsername()).thenReturn("testuser");
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//
//        // Mock the commentRepository
//        Comment savedComment = new Comment();
//        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);
//
//        // Act
//        CommentResponse result = commentService.createNewComment(commentRequest);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("Test comment", result.getComment());
//        // Add more assertions as needed
//    }


    @Test
    public void createNewComment_UserNotAuthenticated_ThrowsUserNotAuthenticatedException() {
        // Arrange
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        CommentRequest commentRequest = new CommentRequest();

        // Mock the SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(null); // Return null email to simulate unauthenticated user
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Act & Assert
        assertThrows(UserNotAuthenticated.class, () -> commentService.createNewComment(commentRequest));
    }



    @Test
    public void viewComment_NonExistentComment_ThrowsResourceNotFoundException() {
        // Arrange
        long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> commentService.viewComment(commentId));
    }

    @Test
    public void viewAllComment_ValidRequest_ReturnsListOfCommentResponses() {
        // Arrange
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setContent("Comment 1");

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setContent("Comment 2");

        List<Comment> commentList = Arrays.asList(comment1, comment2);

        // Mock the commentRepository to return the list of comments
        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.findAll()).thenReturn(commentList);

        // Create an instance of the CommentServiceImpl and set the commentRepository
       commentService.viewAllComment();

        // Act
        List<CommentResponse> result = commentService.viewAllComment();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }


    @Test
    public void editComment_ValidRequest_ReturnsEditCommentResponse() {
        // Arrange
        long commentId = 1L;
        EditCommentRequest editCommentRequest = new EditCommentRequest();
        editCommentRequest.setContent("Updated content");

        Comment comment = new Comment();
        comment.setId(commentId);

        // Mock the commentRepository to return the comment when provided with the commentId
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Act
        EditCommentResponse result = commentService.editComment(commentId, editCommentRequest);

        // Assert
        assertNotNull(result);
        assertEquals(comment, result.getComment());
        assertEquals(editCommentRequest.getContent(), result.getComment());
        // Add additional assertions based on the expected behavior
    }


//    @Test
//    public void editComment_NonExistentComment_ThrowsCommentNotFoundException() {
//        // Arrange
//        long commentId = 1L;
//        EditCommentRequest editCommentRequest = new EditCommentRequest();
//        editCommentRequest.setContent("Updated content");
//
//        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(CommentNotFoundException.class, () -> commentService.editComment(commentId, editCommentRequest));
//    }

//    @Test
//    public void deleteComment_ValidCommentId_ReturnsSuccessMessage() {
//        // Arrange
//        long commentId = 1L;
//        Comment comment = new Comment();
//        comment.setId(commentId);
//
//        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
//
//        // Act
//        String result = commentService.deleteComment(commentId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("comment is successfully deleted", result);
//        // Add additional assertions based on the expected behavior
//    }

    @Test
    public void deleteComment_NonExistentComment_ThrowsResourceNotFoundException() {
        // Arrange
        long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> commentService.deleteComment(commentId));
    }

//    @Test
//    public void viewCommentsByPagination_ValidRequest_ReturnsPaginatedListOfCommentResponses() {
//        // Arrange
//        int pageNo = 0;
//        int pageSize = 10;
//        String sortBy = "content";
//
//        List<Comment> comments = List.of(new Comment(), new Comment());
//        when(commentRepository.findAll()).thenReturn(comments);
//
//        List<CommentResponse> commentResponses = comments.stream()
//                .map(comment -> CommentResponse.builder().comment(comment.getContent()).build())
//                .collect(Collectors.toList());
//        Collections.sort(commentResponses, Comparator.comparing(CommentResponse::getComment, Collections.reverseOrder()));
//
//        // Act
//        Page<CommentResponse> result = commentService.viewCommentsByPagination(pageNo, pageSize, sortBy);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(pageSize, result.getSize());
//        // Add additional assertions based on the expected behavior
//    }
}
