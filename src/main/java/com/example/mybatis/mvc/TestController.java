package com.example.mybatis.mvc;


@ExtController
public class TestController {

  @ExtRequestingMapping("/test")
   public String test(){
       return "2222";
   }
}
