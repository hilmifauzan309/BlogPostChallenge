package com.challenge.blog.service;

import com.challenge.blog.dto.BlogRequestDTO;
import com.challenge.blog.dto.GenericResponseDTO;
import com.challenge.blog.model.BlogModel;
import com.challenge.blog.repository.BlogRepository;
import com.challenge.blog.util.CheckUtils;
import com.challenge.blog.util.GenerateJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public ResponseEntity<Object> save(String title,String body,String author){
        UUID uuid = UUID.randomUUID();

        //dto to model
        BlogModel blogModel = new BlogModel();
        blogModel.setId(uuid.toString());
        blogModel.setTitle(title);
        blogModel.setBody(body);
        blogModel.setAuthor(author);

        //save to db
        blogRepository.save(blogModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(blogModel);
    }

    public ResponseEntity<Object> find(String request){
        Optional<BlogModel> blogModel = blogRepository.findById(request);
        System.out.println("masuk sini " + blogModel);
        if(blogModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDTO().errorResponse("Data Not Found"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(blogModel);
    }

    public ResponseEntity<Object> findAll(BlogRequestDTO requestDto){
        List<BlogModel> listBlogModel;
        Integer pageNo=null;
        Integer pageSize=null;
        String sortBy=null;
        if(requestDto != null) {
            pageNo = requestDto.getPageNo();
            pageSize = requestDto.getPageSize();
            sortBy = requestDto.getSortBy();
        }

        if(pageSize!= null && pageNo != null && sortBy != null){
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            listBlogModel = blogRepository.findAll(pageRequest).getContent();
        } else if(pageSize!= null && pageNo != null){
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
            listBlogModel = blogRepository.findAll(pageRequest).getContent();
        } else if(sortBy != null){
            Sort sort = Sort.by(sortBy);
            listBlogModel = blogRepository.findAll(sort);
        } else {
            listBlogModel = blogRepository.findAll();
        }

        return ResponseEntity.status(HttpStatus.OK).body(listBlogModel);
    }

    public ResponseEntity<Object> update(String id,String title,String body,String author){
        Optional<BlogModel> blogData = blogRepository.findById(id);

        if (blogData.isPresent()) {
            BlogModel blogModel = blogData.get();

            //update data
            if(CheckUtils.IsNullOrEmpty(title)){
                blogModel.setTitle(blogModel.getTitle());
            } else{
                blogModel.setTitle(title);

            }
            if(CheckUtils.IsNullOrEmpty(body)){
                blogModel.setBody(blogModel.getBody());
            }else{
                blogModel.setBody(body);

            }
            if(CheckUtils.IsNullOrEmpty(author)){
                blogModel.setAuthor(blogModel.getAuthor());
            }else{
                blogModel.setAuthor(author);
            }

            //save to db
            blogRepository.save(blogModel);

            return ResponseEntity.status(HttpStatus.OK).body(blogModel);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO().errorResponse("Data not Found"));
        }

    }

    public ResponseEntity<Object> delete(String  request){
        //save to db
        blogRepository.deleteById(request);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDTO().successResponse("Delete Success"));
    }

    public ResponseEntity<Object> checkToken(String username, String token){
        try{
            if(CheckUtils.IsNullOrEmpty(username) && CheckUtils.IsNullOrEmpty(token)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDTO().errorResponse("JWT Fail"));
            }

            Claims claims = GenerateJWT.validateToken(token);

            if(!claims.getId().equals(username)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDTO().errorResponse("Not a token with a username " + username));
            }

        }catch (ExpiredJwtException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDTO().errorResponse("Token expired"));
        }catch (Exception x){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDTO().errorResponse("Token failed"));
        }
        return null;
    }

}
