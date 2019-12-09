package com.qiding.dubbo.provider.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
   @RequestMapping("/hello/{username}")
   public String hello(@PathVariable(value = "username") String username){
       return new StringBuilder(username).append(",你好!!!!!").toString();
   }
}
