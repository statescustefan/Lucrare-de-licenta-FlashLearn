package com.info.uaic.licenta.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "LT_student")
public class Student extends BaseEntity{
	
	@Column(name = "firstname")
	private String first_name;
	
	@Column(name = "lastname")
	private String last_name;
	
	@Column
	private String gender;
	
	@Column
	private Date birthday;
	
	@Column
	private Boolean hasBirthday;
	
	@Column
	private Boolean hasGender;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "image")
	private byte[] image;
	
	@OneToOne(mappedBy= "student")
	private User user;
	
	@OneToMany(mappedBy="student",cascade=CascadeType.ALL)
	private Set<StudentDocument> userDocuments=new HashSet<StudentDocument>();
	

	public Set<StudentDocument> getUserDocuments() {
		return userDocuments;
	}

	public void setUserDocuments(Set<StudentDocument> userDocuments) {
		this.userDocuments = userDocuments;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Boolean getHasBirthday() {
		return hasBirthday;
	}

	public void setHasBirthday(Boolean hasBirthday) {
		this.hasBirthday = hasBirthday;
	}

	public Boolean getHasGender() {
		return hasGender;
	}

	public void setHasGender(Boolean hasGender) {
		this.hasGender = hasGender;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
