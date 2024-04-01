package com.challenge.blog.controller;

import com.challenge.blog.dto.BlogDTO;
import com.challenge.blog.dto.GenericResponseDTO;
import com.challenge.blog.model.BlogModel;
import com.challenge.blog.service.BlogService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/challenge/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveBlogPost(@RequestBody BlogDTO requestDTO){
        String id = requestDTO.getId();
        String title = requestDTO.getTitle();
        String body = requestDTO.getBody();
        String author = requestDTO.getAuthor();
        if(id == null || id.equalsIgnoreCase("")){
            if(title == null || title.equalsIgnoreCase("")
                    && body == null || body.equalsIgnoreCase("")
                    && author == null || author.equalsIgnoreCase("")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDTO().errorResponse("Data cannot be empty"));
            }

            ResponseEntity<Object> response = blogService.save(title,body,author);
            return response;

        } else {
            ResponseEntity<Object> response = blogService.update(id,title,body,author);
            return response;
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Optional<BlogModel>> findBlogPost(@PathVariable("id") String request){

        ResponseEntity<Optional<BlogModel>> response = blogService.find(request);

        return response;
    }

    @PostMapping("/find/all")
    public ResponseEntity<List<BlogModel>> findAllBlogPost(@Nullable @RequestBody BlogDTO requestDTO){

        ResponseEntity<List<BlogModel>> response = blogService.findAll(requestDTO);

        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponseDTO> DeleteBlogPost(@PathVariable("id") String request){

        ResponseEntity<GenericResponseDTO> response = blogService.delete(request);

        return response;
    }
}
