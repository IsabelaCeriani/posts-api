package com.services;

import com.dtos.PostDTO;
import com.models.Post;
import com.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {


    @Autowired
    PostRepository postRepository;

    public Post addPost(PostDTO postDTO) {
        Post post = postDTO.toPost();
        postRepository.save(post);
        return post;

    }

    public Page<Post> getAllUserPosts(UUID id) {
        List<Post> postsList = postRepository.findAllByAuthor(id);
        return new PageImpl<Post>(postsList);
    }

    public Post getPost(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No post with given id"));
    }

    public Post deletePost(UUID id) {
        Post post = getPost(id);
        postRepository.delete(post);
        return post;
    }
}
