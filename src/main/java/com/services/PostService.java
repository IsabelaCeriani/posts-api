package com.services;

import com.dtos.PostDTO;
import com.models.Post;
import com.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Post> getAllUserPosts(UUID id) {
        return postRepository.findAllByAuthor(id);
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
