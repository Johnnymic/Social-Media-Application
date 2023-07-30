package com.michael.socialmedia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Getter
@Setter

@Table(name = "appUser")
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Follow)) return false;
        Follow follow = (Follow) o;
        return Objects.equals(getFollower(), follow.getFollower()) &&
                Objects.equals(getFollowing(), follow.getFollowing());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getFollower(), getFollowing());
    }





}
