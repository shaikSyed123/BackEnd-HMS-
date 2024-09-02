//package com.tsarit.form_1.controller;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tsarit.form_1.model.Appointment;
//import com.tsarit.form_1.model.userData;
//import com.tsarit.form_1.service.AppointmentService;
//
//@RestController
//public class Appointmentcontroller {
//
//	@Autowired
//	private AppointmentService appointmentService;
//
//	@PostMapping("/save")
//	public ModelAndView save(@RequestParam Map<String, String> allParams) {
//		allParams.forEach((key, value) -> System.out.println("Param: " + key + " = " + value));
//		String username = allParams.get("username");
////		appointmentService.saveUserData(username, allParams);
//		System.out.println("user naame from" + username);
//		ModelAndView m = new ModelAndView("redirect:/Appointment");
//		m.addObject("message", "User registered successfully...");
//		
//		 userData user = appointmentService.data();
//		 String firstname = user.getFirstname();
//		    String lastname = user.getLastname();
//		    String hospitalname = user.getHospitalname();
//		m.addObject("firstname", firstname);
//		m.addObject("lastname", lastname);
//		m.addObject("hospitalname", hospitalname);
//		System.out.println(user);
//
//		return m;
//	}
//
//	@GetMapping("/Appointment")
//	public ModelAndView getAllAppointments1(@RequestParam(value = "keyword", required = false) String keyword,
//			@RequestParam(value = "username", required = false) String username) {
//		ModelAndView m = new ModelAndView();
//
//		List<Map<String, Object>> userData = appointmentService.fetchData(username, keyword);
//		m.addObject("appointment", userData);
//		
//		LocalDateTime now = LocalDateTime.now();
//		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
//        String formattedDate = "Date: " + now.format(dateFormatter);
//        String formattedTime = "Time: " + now.format(timeFormatter);
//
//        m.addObject("date", formattedDate);
//        m.addObject("time", formattedTime);
//        
//        userData user = appointmentService.data();
//		 String firstname = user.getFirstname();
//		    String lastname = user.getLastname();
//		    String hospitalname = user.getHospitalname();
//		m.addObject("firstname", firstname);
//		m.addObject("lastname", lastname);
//		m.addObject("hospitalname", hospitalname);
//		System.out.println("user"+user);
//		
//		System.out.println("user name for:" + username);
//		System.out.println("key word :" + keyword);
//		m.setViewName("Appointment");
//		return m;
//	}
//
////	@GetMapping("/delete/{id}")
//	public ModelAndView delete(@RequestParam(value = "username", required = false) String username,
//			@PathVariable("id") int id) {
////		appointmentService.delete(username, id);
//		ModelAndView modelAndView = new ModelAndView("redirect:/Appointment");
//		modelAndView.addObject("message", "User deleted successfully...");
//
//		return modelAndView;
//
//	}
//
//	@GetMapping("/today")
//	public ModelAndView getTodaysAppointments(@RequestParam(value = "username", required = false) String username) {
//		LocalDate today = LocalDate.now();
//		List<Map<String, Object>> list = appointmentService.getAppointmentsByDate(username, today);
//		ModelAndView m = new ModelAndView();
//		m.setViewName("Appointment");
//		m.addObject("appointment", list);
//		
//		LocalDateTime now = LocalDateTime.now();
//		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
//        String formattedDate = "Date: " + now.format(dateFormatter);
//        String formattedTime = "Time: " + now.format(timeFormatter);
//
//        m.addObject("date", formattedDate);
//        m.addObject("time", formattedTime);
//        
//        userData user = appointmentService.data();
//		 String firstname = user.getFirstname();
//		    String lastname = user.getLastname();
//		    String hospitalname = user.getHospitalname();
//		m.addObject("firstname", firstname);
//		m.addObject("lastname", lastname);
//		m.addObject("hospitalname", hospitalname);
//		System.out.println("user"+user);	
//		
//		return m;
//	}
//
//	@GetMapping("/DoctorView")
//	public ModelAndView getTodayCount(String username, Model model) {
//		long todayCount = appointmentService.countTodayAppointments(username);
//		ModelAndView m = new ModelAndView("DoctorView");
//		m.addObject("todayCount", todayCount);
//		System.out.println("Today Count: " + todayCount);
//		
//		userData user = appointmentService.data();
//		 String firstname = user.getFirstname();
//		    String lastname = user.getLastname();
//		    String hospitalname = user.getHospitalname();
//		m.addObject("firstname", firstname);
//		m.addObject("lastname", lastname);
//		m.addObject("hospitalname", hospitalname);
//		System.out.println("user"+user);		
//		return m;
//	}
//
//	@GetMapping("/count")
//	public ModelAndView getDashboard(String username) {
//		long patientCount = appointmentService.TotalPatientCount(username);
//		ModelAndView m = new ModelAndView("count");
////        m.setViewName("dashboard");
//		m.addObject("patientCount", patientCount);
//		
//		 userData user = new userData();
//		 String firstname = user.getFirstname();
//		    String lastname = user.getLastname();
//		    String hospitalname = user.getHospitalname();
//		m.addObject("firstname", firstname);
//		m.addObject("lastname", lastname);
//		m.addObject("hospitalname", hospitalname);
//		System.out.println(user);
//		System.out.println(m);
//		return m;
//	}
//	
//	
//
//	@GetMapping("/editAppointment/{id}")
//	public ModelAndView showUpdateForm( String username, @PathVariable("id") int id) {
//	    Map<String, Object> appointment = appointmentService.getAppointmentById(username, id);
//	    ModelAndView m = new ModelAndView();
//
//	    m.setViewName("editAppointment");
//	    m.addObject("appointment", appointment);
//
//	    return m;
//	}
//	
//	
//	@PostMapping("/update")
//	public ModelAndView updateAppointment(
//	        @RequestParam("id") Integer id,
//	        @RequestParam("username") String username,
//	        @RequestParam("firstName") String firstName,
//	        @RequestParam("lastName") String lastName,
//	        @RequestParam("phoneNumber") String phoneNumber,
//	        @RequestParam("email") String email,
//	        @RequestParam(value = "age", required = false) Integer age,
//	        @RequestParam(value = "gender", required = false) String gender,
//	        @RequestParam(value = "weight", required = false) Double weight) {
//
//	    // Debug output
//	    System.out.println("Received parameters: id=" + id + ", age=" + age + ", weight=" + weight);
//
//	    // Update the appointment
//	    appointmentService.updateAppointment(username, id, firstName, lastName, phoneNumber, email, age, gender, weight);
//
//	    ModelAndView modelAndView = new ModelAndView("redirect:/Appointment");
//	    modelAndView.addObject("message", "Appointment updated successfully!");
//
//	    return modelAndView;
//	}
//
//
//
//}
