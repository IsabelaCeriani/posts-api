package com.dtos;

import com.models.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {


    private UUID author;
    private String content;


    public PostDTO(){}

    public Post toPost(){
        return new Post(author, content, new ArrayList<>());
    }

}
