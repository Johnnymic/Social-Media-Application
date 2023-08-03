package com.michael.socialmedia.dto.response;

import com.michael.socialmedia.domain.Comment;
import com.michael.socialmedia.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostResponse {
    private Long id;

    private String content;



}
