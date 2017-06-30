package com.info.uaic.licenta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.info.uaic.licenta.model.User;
import com.info.uaic.licenta.repository.StudentRepository;
import com.info.uaic.licenta.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findUserByEmail(email);
	}

	public User findByUsername(String username) {
		return userRepository.findByUserName(username);
	}

	public void save(User userRegistered) {
		// TODO Auto-generated method stub
		studentRepository.save(userRegistered.getStudent());
		userRegistered.setPassword(bCryptPasswordEncoder.encode(userRegistered.getPassword()));
		userRepository.save(userRegistered);
	}
	
	

}
