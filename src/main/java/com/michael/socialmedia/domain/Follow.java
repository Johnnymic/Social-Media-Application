package com.michael.socialmedia.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter

@Table(name = "appUser")
@Entity
@NoArgsConstructor

public class Follow  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @ManyToOne
     @JoinColumn(name = "user_id")
     private  User following;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;



}
