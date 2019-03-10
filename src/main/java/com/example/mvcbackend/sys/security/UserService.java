package com.example.mvcbackend.sys.security;

import com.example.mvcbackend.sys.base.BaseService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User,UserMapper> implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return dao.find(new User(s,null));
    }
}
