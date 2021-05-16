package com.lige110.springreact.ppmtool.services;


import com.lige110.springreact.ppmtool.domain.User;
import com.lige110.springreact.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.lige110.springreact.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }

    }



}
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public User saveUser (User newUser){
//
//        try{
//
//            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
//
//
//            // Username have be unique
//            newUser.setUsername(newUser.getUsername());
//            // Make sure that password and confirmPassword match, do not show confirmPassword
//
//            System.out.println(newUser);
//            return userRepository.save(newUser);
//
//        }catch (Exception e){
//            throw new UsernameAlreadyExistsException("Username '" +newUser.getUsername()+"' already exists!");
//        }
//
//
//    }
//
//}
