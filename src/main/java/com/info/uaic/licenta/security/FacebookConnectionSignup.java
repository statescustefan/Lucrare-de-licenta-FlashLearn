package com.info.uaic.licenta.security;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

import com.info.uaic.licenta.model.Student;
import com.info.uaic.licenta.model.User;
import com.info.uaic.licenta.repository.StudentRepository;
import com.info.uaic.licenta.repository.UserRepository;



@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;


	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String execute(Connection<?> connection) {
		// TODO Auto-generated method stub
		System.out.println("signup with facebook");
		String username = connection.getDisplayName();

		if (userRepository.findByUserName(username) == null) {
			Facebook facebook = (Facebook) connection.getApi();
			String[] userFields = { "email" };
			String[] birthdayField = {"birthday" };
			String[] studentFields = { "gender", "first_name", "last_name" };
			Student student = facebook.fetchObject("me", Student.class, studentFields);
	        String email = facebook.fetchObject("me", String.class, userFields);
			String birthday = facebook.fetchObject("me", String.class, birthdayField);
			
	        if(!checkForEmptyFacebookInformation(birthday)){
	        	try {
					student.setBirthday(formatFacebookDate(birthday));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } else {
	        	student.setHasBirthday(false);
	        }
	        student.setImage(facebook.userOperations().getUserProfileImage());
	        student.setHasGender(true);
			studentRepository.save(student);
			User user = new User();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(student.getFirst_name()));
			if (!checkForEmptyFacebookInformation(email)) {
			  user.setEmail(email);	
			}
			user.setStudent(student);
			userRepository.save(user);
		}
		return username;
	}
	
	private boolean checkForEmptyFacebookInformation(String string){
		if (string.contains("id")) {
			return true;
		}
		return false;
	}
	
	private Date formatFacebookDate(String date) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        return formatter.parse(date);
	}
}
