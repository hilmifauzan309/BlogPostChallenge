//package com.hilmi.day14_jwt.Controllers;
//
//import com.hilmi.day14_jwt.Models.EmployeeModel;
//import com.hilmi.day14_jwt.Services.EmployeeService;
//import com.hilmi.day14_jwt.Utils.CheckUtils;
//import com.hilmi.day14_jwt.Utils.Constant;
//import com.hilmi.day14_jwt.Utils.GenerateJWT;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.SignatureException;
//import org.apache.catalina.filters.ExpiresFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@RestController
//@RequestMapping("day14_jwt/api/v1")
//public class EmlpoyeeController {
//
//    @Autowired
//    EmployeeService es;
//
//    @PostMapping("createJwt")
//    public ResponseEntity<Map<String, Object>>login(
//            @RequestBody final Map<String,Object> request
//    ) {
//        Map<String, Object> response = new HashMap<>();
//
//        String username = request.get("username").toString();
//        String password = request.get("password").toString();
//
//        Optional<EmployeeModel> cek = es.findUsername(username);
//
//        if(cek.isEmpty()){
//            response.put(Constant.STATUS, "username tidak terdaftar");
//        }else{
//            if(password.equals(cek.get().getPassword())){
//                String token = GenerateJWT.createToken(username);
//                response.put(Constant.TOKEN, token);
//                response.put(Constant.STATUS, "login berhasil");
//            }else{
//                response.put(Constant.STATUS, "password salah");
//            }
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("checkJwt")
//    public ResponseEntity<Map<String,Object>>getData(
//            @RequestHeader(required = false, value = "username") String username,
//            @RequestHeader(required = false, value = "token") String token
//
//    ) throws ParseException {
//        Map<String,Object> response = new HashMap<>();
//
//
//
//        try{
//
//            if(CheckUtils.IsNullOrEmpty(username) && CheckUtils.IsNullOrEmpty(token)){
//                response.put(Constant.STATUS,"JWT Fail");
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//
//            Claims claims = GenerateJWT.validateToken(token);
//
//            if(!claims.getId().equals(username)){
//                response.put(Constant.STATUS,"Bukan token dengan username " + username);
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//
//        }catch (ExpiredJwtException e){
//            response.put(Constant.STATUS,"Token expired");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }catch (SignatureException signature){
//            response.put(Constant.STATUS,"Token failed");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }catch (Exception x){
//            response.put(Constant.STATUS,"Token failed");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String strDate = dateFormat.format(date);
//        Date d1 = dateFormat.parse(strDate);
//        long waktu = d1.getTime();
//        long detik = (waktu  / 1000) % 60;
//        String time =String.valueOf(detik);
//
//        if(time.contains("5")){
//            response.put(Constant.STATUS,"JWT " + strDate + " Eksad");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }else{
//            response.put(Constant.STATUS,"JWT " + strDate + " Java");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//    }
//}
