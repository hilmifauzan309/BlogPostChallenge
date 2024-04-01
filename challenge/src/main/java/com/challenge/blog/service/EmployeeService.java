package com.challenge.blog.service;

import com.challenge.blog.dto.GenericResponseDTO;
import com.challenge.blog.model.EmployeeModel;
import com.challenge.blog.repository.EmployeeRepository;
import com.challenge.blog.util.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Optional<EmployeeModel> findUsername(String username){
        return employeeRepository.findByUsername(username);
    }

    public ResponseEntity<Object> save(String username, String password){

        //dto to model
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setUsername(username);
        employeeModel.setPassword(password);

        //save to db
        employeeRepository.save(employeeModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(employeeModel);
    }

    public ResponseEntity<Object> update(Integer id,String username,String password){
        Optional<EmployeeModel> blogData = employeeRepository.findById(id);

        if (blogData.isPresent()) {
            EmployeeModel employeeModel = blogData.get();

            //update data
            if(CheckUtils.IsNullOrEmpty(username)){
                employeeModel.setUsername(employeeModel.getUsername());
            } else{
                employeeModel.setUsername(username);

            }
            if(CheckUtils.IsNullOrEmpty(password)){
                employeeModel.setPassword(employeeModel.getPassword());
            }else{
                employeeModel.setPassword(password);

            }

            //save to db
            employeeRepository.save(employeeModel);

            return ResponseEntity.status(HttpStatus.OK).body(employeeModel);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO().errorResponse("Data not Found"));
        }

    }
}
