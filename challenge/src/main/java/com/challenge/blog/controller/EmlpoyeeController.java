package com.challenge.blog.controller;

import com.challenge.blog.dto.EmployeeRequestDTO;
import com.challenge.blog.dto.GenericResponseDTO;
import com.challenge.blog.model.EmployeeModel;
import com.challenge.blog.service.EmployeeService;
import com.challenge.blog.util.CheckUtils;
import com.challenge.blog.util.Constant;
import com.challenge.blog.util.GenerateJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/challenge/employee")
public class EmlpoyeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveBlogPost(@RequestBody EmployeeRequestDTO requestDTO){
        Integer id = requestDTO.getId();
        String username = requestDTO.getUsername();
        String password = requestDTO.getPassword();
        ResponseEntity<Object> response;
        if(id == null){
            if(CheckUtils.IsNullOrEmpty(username) && CheckUtils.IsNullOrEmpty(password)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponseDTO().errorResponse("Data cannot be empty"));
            }
            response = employeeService.save(username,password);
            return response;

        } else {
            response = employeeService.update(id,username,password);
            return response;
        }
    }

    @PostMapping("/create/jwt")
    public ResponseEntity<Map<String, Object>>login(
            @RequestBody final Map<String,String> request
    ) {
        Map<String, Object> response = new HashMap<>();

        String username = request.get("username");
        String password = request.get("password");

        Optional<EmployeeModel> check = employeeService.findUsername(username);

        if(check.isEmpty()){
            response.put(Constant.STATUS, "Username not registered");
        }else{
            if(password.equals(check.get().getPassword())){
                String token = GenerateJWT.createToken(username);
                response.put(Constant.TOKEN, token);
                response.put(Constant.STATUS, "JWT Created");
            }else{
                response.put(Constant.STATUS, "Wrong Password");
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
