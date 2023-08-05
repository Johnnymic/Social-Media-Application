package com.michael.socialmedia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@EqualsAndHashCode
@Table(name = "follower")
@Entity
@NoArgsConstructor

public class Follow  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "follower_user_id")
    private User follower;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "following_user_id")
    private User following;





}
