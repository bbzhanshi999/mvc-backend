package com.example.mvcbackend.app.web;


import com.example.mvcbackend.sys.security.User;
import com.example.mvcbackend.sys.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SignController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping(path="/api/signIn",consumes = "application/json")
    public User signIn(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping(path="/api/signUp",consumes = "application/json")
    public User signUp(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        return user;
    }

    @RequestMapping(path="/api/hehe")
    public String hehe(){
        return "hehe";
    }
}
