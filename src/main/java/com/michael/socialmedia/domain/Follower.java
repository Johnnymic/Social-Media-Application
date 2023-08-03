package com.michael.socialmedia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Builder
@Table(name = "appUser")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @ManyToOne
     @JoinColumn(name = "user_id")
     private  User user;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;


}
