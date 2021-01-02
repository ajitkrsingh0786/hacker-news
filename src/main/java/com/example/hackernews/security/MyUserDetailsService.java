package com.example.hackernews.security;

import com.example.hackernews.entity.User;
import com.example.hackernews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);

        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + s));
        return new MyUserDetails(user.get());
//        return user.map(MyUserDetails::new).get();
//        return user.map(MyUserDetails::new).get();h
    }
}
