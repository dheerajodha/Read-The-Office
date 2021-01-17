package com.springboot.rest.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Quote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	
	@Column(name="CONTENT", columnDefinition="CLOB NOT NULL") 
    @Lob
	private String content;
	
	public Quote() {
		super();
	}

	public Quote(Long id, String firstName, String lastName, String content) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.content = content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String studentName) {
		this.firstName = studentName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String passportNumber) {
		this.lastName = passportNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
