package com.services;

import com.dtos.PostDTO;
import com.models.Post;
import com.pageConfig.RestResponsePage;
import com.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {


    @Autowired
    PostRepository postRepository;

    @Autowired
    private WebClient followApi;

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(7);

    private final Logger logger = Logger.getLogger(this.getClass().getName());



    @Bean
    public WebClient followApi() {
        return WebClient.create("http://localhost:8089");
    }


    public Post addPost(PostDTO postDTO) {
        Post post = postDTO.toPost();
        postRepository.save(post);
        logger.info("Added post: " + post.getId());
        return post;

    }

    @Bean
    public RestTemplate restTemplate(List<HttpMessageConverter<?>> messageConverters) {
        return new RestTemplate(messageConverters);
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        return new ByteArrayHttpMessageConverter();
    }

    public Page<Post> getAllUserPosts(UUID id) {
        List<Post> postsList = postRepository.findAllByAuthor(id);
        logger.info("Return of users posts, size =" + postsList.size());
        return new PageImpl<Post>(postsList);
    }

    public Post getPost(UUID id) {
        logger.info("get_post=" + id);
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No post with given id"));
    }

    public Post deletePost(UUID id) {
        Post post = getPost(id);
        postRepository.delete(post);
        logger.info("post_deleted=" + id);
        return post;
    }



    public Page<Post> getFeedPosts(UUID id) throws Exception {
//        RestResponsePage followingUsersId = followApi.get().uri("follows/getFollowing/" + id).retrieve().toEntity(RestResponsePage.class);
//        System.out.println(followingUsersId.);
//
        String uri = String.format("http://localhost:8080/follows/getFollowingList/%s", id);
        RestTemplate restTemplate  = new RestTemplate();
        List<String> followingUsersId = restTemplate.getForObject(uri, List.class);

        if(followingUsersId == null) throw new Exception("Null returned from follows service");
        List<Post> feed =  followingUsersId.parallelStream().flatMap(userId -> new ArrayList<>(postRepository.findByAuthor(UUID.fromString(userId)))
                         .parallelStream()
                         .sorted(Comparator.comparing(Post::getCreatedAt)))
                         .collect(Collectors.toList());

        logger.info(String.format("Return of feed posts, size =%s", feed.size()));
        return new PageImpl<>(feed);
    }

    public Post answerPost(UUID parentId, PostDTO postDTO) {
        Post newPost = postDTO.toPost();
        postRepository.save(newPost);
        Post parentPost = postRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Could not find Post with given \"parentId\" "));
        parentPost.getThreadAnswers().add(newPost);
        postRepository.save(parentPost);
        logger.info(String.format("Added answer to post: %s", newPost.getId()));
        return parentPost;
    }

    public Page<Post> getAllPosts() {
        return new PageImpl<>(postRepository.findAll());
    }
}
