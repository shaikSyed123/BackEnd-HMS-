//package com.tsarit.form_1.model;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class Appointment {
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
//	public long getPhonenumber() {
//		return phonenumber;
//	}
//
//	public void setPhonenumber(long phonenumber) {
//		this.phonenumber = phonenumber;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public Appointment() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	
//
//	public LocalDate getDate() {
//		return date;
//	}
//
//	public Appointment(int id, String firstName, String lastName, long phonenumber, String gmail, int age,
//			String gender, int weight, LocalDate date, LocalTime time, String email) {
//		super();
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.phonenumber = phonenumber;
//		this.email = email;
//		this.age = age;
//		this.gender = gender;
//		this.weight = weight;
//		this.date = date;
//		this.time = time;
//	}
//
//	public void setDate(LocalDate date) {
//		this.date = date;
//	}
//
//	public LocalTime getTime() {
//		return time;
//	}
//
//	public void setTime(LocalTime time) {
//		this.time = time;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}
//
//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//
//	public int getWeight() {
//		return weight;
//	}
//
//	public void setWeight(int weight) {
//		this.weight = weight;
//	}
//
//	@Override
//	public String toString() {
//		return "Appointment [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phonenumber="
//				+ phonenumber + ", email=" + email + ", age=" + age + ", gender=" + gender + ", weight=" + weight
//				+ ", date=" + date + ", time=" + time + "]";
//	}
//
//	
//
//}
