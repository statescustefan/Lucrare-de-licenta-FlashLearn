package com.info.uaic.licenta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.info.uaic.licenta.model.StudentDocument;

public interface StudentDocumentRepository extends CrudRepository<StudentDocument, Long>{

	@Query("from StudentDocument where student.id = :studentId")
	List<StudentDocument> findByStudentId(@Param("studentId") Long studentId);
}
