package com.controllers;

import com.dtos.PostDTO;
import com.models.Post;
import com.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<Post> addPost(@RequestBody PostDTO postDTO){
        return ResponseEntity.ok(postService.addPost(postDTO));
    }

    @GetMapping("/getUserPosts/{id}")
    public ResponseEntity<Page<Post>> getUserPosts(@PathVariable UUID id){
        return ResponseEntity.ok(postService.getAllUserPosts(id));
    }

    @GetMapping("/getPost/{id}")
    public ResponseEntity<Post> getPost(@PathVariable UUID id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable UUID id){
        return ResponseEntity.ok(postService.deletePost(id));
    }
}
