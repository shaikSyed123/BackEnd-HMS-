package com.tsarit.form_1.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_data")
public class userData implements UserDetails {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
     @Column(nullable = false)
	 private String firstname;
     @Column(nullable = false)
	 private String lastname;
     @Column(nullable = false)
	 private String hospitalname;
     @Column(nullable = false,unique = true)
	 private String emailid;
     @Column(nullable = false,unique = true)
	 private String phonenumber;
//     @Column(nullable = false)
     private String address;
     @Column(nullable = false)
	 private String password;
     @Column(nullable = false)
	 private String repetepassword;
     private LocalDateTime lastLoginTimeDate;
     
    
     public LocalDateTime getLastLoginTimeDate() {
		return lastLoginTimeDate;
	}
	public void setLastLoginTimeDate(LocalDateTime lastLoginTimeDate) {
		this.lastLoginTimeDate = lastLoginTimeDate;
	}
	private String role;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "userData [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", hospitalname="
				+ hospitalname + ", emailid=" + emailid + ", phonenumber=" + phonenumber + ", password=" + password
				+ ", repetepassword=" + repetepassword + "]";
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getHospitalname() {
		return hospitalname;
	}
	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepetepassword() {
		return repetepassword;
	}
	public void setRepetepassword(String repetepassword) {
		this.repetepassword = repetepassword;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return getEmailid();
	}
}
