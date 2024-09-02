//package com.tsarit.form_1.model;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//public class newPatients {
//  
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	@Column(nullable = false)
//	private String firstName;
//	@Column(nullable = false)
//	private String lastName;
//	@Column(nullable = false)
//	private long phonenumber;
//	@Column(nullable = false)
//	private String email;
//	@Column(nullable = false)
//	private int age;
//	@Column(nullable = false)
//	private String gender;
//	@Column(nullable = false)
//	private int weight;
//	@Column(nullable = false)
//	@CreationTimestamp
//	private LocalDate date;
//	@CreationTimestamp
//	private LocalTime time;
//}
