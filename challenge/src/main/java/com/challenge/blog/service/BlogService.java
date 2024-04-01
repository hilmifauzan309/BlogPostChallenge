package com.challenge.blog.service;

import com.challenge.blog.dto.BlogDTO;
import com.challenge.blog.dto.GenericResponseDTO;
import com.challenge.blog.model.BlogModel;
import com.challenge.blog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ResponseEntity<Optional<BlogModel>> find(String request){
        Optional<BlogModel> blogModel = blogRepository.findById(request);
        return ResponseEntity.status(HttpStatus.OK).body(blogModel);
    }

    public ResponseEntity<List<BlogModel>> findAll(BlogDTO requestDto){
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
            if(title == null || title.equalsIgnoreCase("")){
                blogModel.setTitle(blogModel.getTitle());
            } else{
                blogModel.setTitle(title);

            }
            if(body == null || body.equalsIgnoreCase("")){
                blogModel.setBody(blogModel.getBody());
            }else{
                blogModel.setBody(body);

            }
            if(author == null || author.equalsIgnoreCase("")){
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

    public ResponseEntity<GenericResponseDTO> delete(String  request){
        //save to db
        blogRepository.deleteById(request);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDTO().successResponse("Delete Success"));
    }

}
