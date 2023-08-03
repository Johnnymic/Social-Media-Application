package com.michael.socialmedia.token;

import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Token {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long Id;

    private String token;

    private boolean isExpired;

    private boolean isRevoked;
//    @Enumerated(EnumType.STRING)
//    private TokenType tokenType;
    @ManyToOne()
    @JoinColumn(name = "user_Id" )
    private User user;

}
