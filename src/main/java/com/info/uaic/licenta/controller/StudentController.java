package com.info.uaic.licenta.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.info.uaic.licenta.model.FileBucket;
import com.info.uaic.licenta.model.StudentDocument;
import com.info.uaic.licenta.model.User;
import com.info.uaic.licenta.service.StudentService;
import com.info.uaic.licenta.service.UserService;

@Controller
@RequestMapping(value = "/FlashLearn/student")
public class StudentController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping(value = "/dashboard")
	public ModelAndView dashboard(Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("loggeduser", userService.findByUsername(principal.getName()));
		modelAndView.setViewName("view/homePage");
		return modelAndView;
	}
	
	@GetMapping(value = "/uploads")
	public ModelAndView uploads(Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		FileBucket uploadFile = new FileBucket();
		User user = userService.findByUsername(principal.getName());
		modelAndView.addObject("loggeduser", user);
		modelAndView.addObject("uploadFile", uploadFile);
		modelAndView.addObject("documents", studentService.getStudentDocuments(user.getId()));
		modelAndView.setViewName("view/uploadPage");
		return modelAndView;
	}
	
	@GetMapping(value = "/uploads/open-document-{docid")
	@ResponseStatus(value = HttpStatus.OK)
	public void openStudentDocument(@PathVariable("docid")Long docid, HttpServletResponse response) throws IOException {
		StudentDocument document=studentService.findStudentDocumentById(docid);
		response.setContentType(document.getType());
		response.setContentLength(document.getContent().length);
		response.setHeader("Content-Disposition", "inline; filename=\"" + document.getName() + "\"");
		FileCopyUtils.copy(document.getContent(), response.getOutputStream());
	}
	
	@GetMapping(value = "/uploads/download-document-{docid}")
	@ResponseStatus(value = HttpStatus.OK)
	public void downloadStudentDocument(@PathVariable("docid")Long docid,HttpServletResponse response) throws IOException{
		StudentDocument document=studentService.findStudentDocumentById(docid);
		response.setContentType(document.getType());
		response.setContentLength(document.getContent().length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + document.getName() + "\"");
		FileCopyUtils.copy(document.getContent(), response.getOutputStream());
	}
	
	@GetMapping(value = "/uploads/delete-document-{docid}")
	public ModelAndView deleteStudentDocument(@PathVariable("docid")Long docid){
		ModelAndView modelAndView = new ModelAndView();
		studentService.deleteStudentDocument(docid);
		modelAndView.setViewName("redirect:/FlashLearn/student/uploads");
		return modelAndView;
	}
	
	@PostMapping(value = "/uploads")
	public ModelAndView uploadFile(@ModelAttribute("uploadFile") FileBucket uploadFile, Principal principal) throws IOException, ParseException{
		ModelAndView modelAndView = new ModelAndView();
		if(uploadFile.getFile().getContentType().compareTo("application/x-msdownload")==0){
			modelAndView.addObject("documentUploadError","This format is not allowed");
			modelAndView.setViewName("redirect:/FlashLearn/student/uploadManager");
			return modelAndView;
		}
		User user = userService.findByUsername(principal.getName());
		studentService.saveStudentDocument(uploadFile, user.getStudent());
		modelAndView.setViewName("redirect:/FlashLearn/student/uploads");
		return modelAndView;
	}
	
}
