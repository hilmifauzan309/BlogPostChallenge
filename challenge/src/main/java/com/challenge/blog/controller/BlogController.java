package com.challenge.blog.controller;

import com.challenge.blog.dto.BlogRequestDTO;
import com.challenge.blog.dto.GenericResponseDTO;
import com.challenge.blog.service.BlogService;
import com.challenge.blog.util.CheckUtils;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenge/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    //Create or Update blog post
    @PostMapping("/save")
    public ResponseEntity<Object> saveBlogPost(@RequestBody BlogRequestDTO requestDTO,
                                               @RequestHeader(required = false, value = "username") String username,
                                               @RequestHeader(required = false, value = "token") String token){

        ResponseEntity<Object> responseToken = blogService.checkToken(username,token);
        if(responseToken == null) {
            String id = requestDTO.getId();
            String title = requestDTO.getTitle();
            String body = requestDTO.getBody();
            String author = requestDTO.getAuthor();
            if (CheckUtils.IsNullOrEmpty(id)) {
                if (CheckUtils.IsNullOrEmpty(title) || CheckUtils.IsNullOrEmpty(body) || CheckUtils.IsNullOrEmpty(author)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDTO().errorResponse("Data cannot be empty"));
                }
                ResponseEntity<Object> response = blogService.save(title, body, author);
                return response;

            } else {
                ResponseEntity<Object> response = blogService.update(id, title, body, author);
                return response;
            }
        } else {
            return responseToken;
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findBlogPost(@PathVariable("id") String request,
                                               @RequestHeader(required = false, value = "username") String username,
                                               @RequestHeader(required = false, value = "token") String token){
        ResponseEntity<Object> responseToken = blogService.checkToken(username,token);
        if(responseToken == null) {
            ResponseEntity<Object> response = blogService.find(request);

            return response;
        } else {
            return responseToken;
        }
    }

    @PostMapping("/find/all")
    public ResponseEntity<Object> findAllBlogPost(@Nullable @RequestBody BlogRequestDTO requestDTO,
                                                  @RequestHeader(required = false, value = "username") String username,
                                                  @RequestHeader(required = false, value = "token") String token){
        ResponseEntity<Object> responseToken = blogService.checkToken(username,token);
        if(responseToken == null) {
            ResponseEntity<Object> response = blogService.findAll(requestDTO);

            return response;
        } else {
            return responseToken;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> DeleteBlogPost(@PathVariable("id") String request,
                                                 @RequestHeader(required = false, value = "username") String username,
                                                 @RequestHeader(required = false, value = "token") String token){
        ResponseEntity<Object> responseToken = blogService.checkToken(username,token);
        if(responseToken == null) {
            ResponseEntity<Object> response = blogService.delete(request);

            return response;
        }  else {
            return responseToken;
        }
    }
}
