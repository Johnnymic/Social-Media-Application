package com.michael.socialmedia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public  abstract class BaseEntity {


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_At")
    private Date createdAt;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_At")
    private Date updatedAt;

    @PrePersist
    public void  createdAt() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void updatedAt(){
        this.updatedAt = new Date();
    }
}
