package com.michael.socialmedia.dto.request;

import com.michael.socialmedia.domain.Comment;
import com.michael.socialmedia.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PostRequest {

    private Long id;

    private String Content;

    private  int likeCount;
    ;
    private Date createAt;

    private  User user;

}
