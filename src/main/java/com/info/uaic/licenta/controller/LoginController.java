package com.info.uaic.licenta.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.info.uaic.licenta.model.User;
import com.info.uaic.licenta.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping(value = {"/" , "/login"})
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("view/loginPage");
		modelAndView.addObject("userRegistered", new User());
		return modelAndView;
	}
	
	@GetMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	    	new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}
	
	@PostMapping(value = "/register")
	public ModelAndView register(@ModelAttribute("userRegistered")User userRegistered, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(userRegistered.getEmail());
		if (userExists != null) {
			bindingResult
			.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("content/registration");
		} else {
			userService.save(userRegistered);
			modelAndView.setViewName("redirect:/login");
		}
		return modelAndView;
	}

}
