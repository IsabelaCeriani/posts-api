package com.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import java.util.Date;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Post {


    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID id;

    private UUID author;
    private String content;
    private UUID parentPost;

    private Date createdAt;

    private int likes;




    public Post(){

    }

    public Post(UUID author, String content, UUID parentPost) {
        this.author = author;
        this.content = content;
        this.parentPost = parentPost;
        this.likes = 0;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}
