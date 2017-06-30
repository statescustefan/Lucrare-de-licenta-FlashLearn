package com.info.uaic.licenta.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.info.uaic.licenta.model.FileBucket;
import com.info.uaic.licenta.model.Student;
import com.info.uaic.licenta.model.StudentDocument;
import com.info.uaic.licenta.repository.StudentDocumentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentDocumentRepository studentDocumentRepository;
	
	public List<StudentDocument> getStudentDocuments(Long studentId) {
		return studentDocumentRepository.findByStudentId(studentId);
	}

	public StudentDocument findStudentDocumentById(Long docid) {
		return studentDocumentRepository.findOne(docid);
	}

	public void deleteStudentDocument(Long docid) {
		studentDocumentRepository.delete(docid);
	}
	
	public void saveStudentDocument(FileBucket fileBucket,Student student) throws IOException, ParseException{
		StudentDocument document=new StudentDocument();
		MultipartFile multipartFile=fileBucket.getFile();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		Date d1=dateFormat.parse(dateFormat.format(cal.getTime()));
		document.setName(multipartFile.getOriginalFilename());
		document.setDescription(fileBucket.getDescription());
		document.setType(multipartFile.getContentType());
		document.setContent(multipartFile.getBytes());
		document.setStudent(student);
		document.setDate(d1);
		document.setUploaddate(dateFormat.format(d1));
		studentDocumentRepository.save(document);
	}
}
