package com.challenge.blog.repository;

import com.challenge.blog.model.BlogModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogModel,String> {

    BlogModel findByTitle(String Title);

}
