package com.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.LocalDateTimeType;
import org.springframework.data.domain.Page;

import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

    @OneToMany
    private List<Post> threadAnswers;

    private LocalDateTime createdAt;

    private int likes;




    public Post(){

    }

    public Post(UUID author, String content, List<Post> threadAnswers) {
        this.author = author;
        this.content = content;
        this.threadAnswers = threadAnswers;
        this.likes = 0;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
