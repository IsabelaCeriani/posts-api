package com.dtos;

import com.models.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {


    private UUID author;
    private String content;
    private UUID parentPost;


    public Post toPost(){
        return new Post(author, content, parentPost);
    }

}
