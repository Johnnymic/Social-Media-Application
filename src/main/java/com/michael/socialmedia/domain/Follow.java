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
     private  User follower;

    @ManyToOne
    private User following;



}
