//package com.tsarit.form_1.controller;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tsarit.form_1.config.JwtUtil;
//import com.tsarit.form_1.model.userData;
//import com.tsarit.form_1.repository.userRepository;
//import com.tsarit.form_1.service.AppointmentService;
//
//
//
//@Controller
////@RequestMapping("/user")
//public class userController {
//	
//	@Autowired
//	private AppointmentService appointmentService;
//	
//	@Autowired
//	private JwtUtil jwtUtii;
//	
//	@Autowired
//	private userRepository repository;
//	
//	@Autowired
//	private BCryptPasswordEncoder bp;
//
////	@GetMapping("/")
////	public String home() {
////		return "index";
////	}
//	
//	@PostMapping("/login")
//	public String login() {
//		return "login";
//	}
//	@GetMapping("/login")
//	public String login2() {
//		return "login";
//	}
//		
//	@GetMapping("/home")
//	public String m1() {
//		return "home";
//	}
////	@GetMapping("/dashboard")
////	public ModelAndView getDashboard(@RequestParam(value = "username", required = false) String username) {
////		long patientCount = appointmentService.PatientCount(username);
////		ModelAndView m = new ModelAndView();
////        m.setViewName("dashboard");
////		m.addObject("patientCount", patientCount);
////		
////		 userData user = appointmentService.data();
////		 String firstname = user.getFirstname();
////		    String lastname = user.getLastname();
////		    String hospitalname = user.getHospitalname();
////		m.addObject("firstname", firstname);
////		m.addObject("lastname", lastname);
////		m.addObject("hospitalname", hospitalname);
////		
////		System.out.println(user);
////		System.out.println(m);
////		return m;
////	}
//	
//
//   
//
//
//	
//	@GetMapping("/Accounts")
//	public String e1() {
//		return "Accounts";
//	}
//	
//	@GetMapping("/editAppointment")
//     public String getMethodName() {
//		return "editAppointment";
//	}	
//
//	
//	@GetMapping("/NewAppointment")
//	public ModelAndView newappointment() {
//		ModelAndView m=new ModelAndView("NewAppointment");
//		 userData user = appointmentService.data();
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
//	@GetMapping("/name")
//	public String m1(userData user, Model model) {
//	    String firstname = user.getFirstname();
//	    String lastname = user.getLastname();
//	    String hospitalname = user.getHospitalname();
//	    
//	    model.addAttribute("firstname", firstname);
//	    model.addAttribute("lastname", lastname);
//	    model.addAttribute("hospitalname", hospitalname);
//	    
//	    return "count";
//	}
////	 @PostMapping(value = "/yourEndpoint", consumes = "application/x-www-form-urlencoded")
////	    public ResponseEntity<?> handleFormRequest(@ModelAttribute Map<String, String> formdate) {
////	        // Handle form data
////	        return ResponseEntity.ok("Form data received");
////	    }
//	
////	@PostMapping("/register")
////	public String register(@ModelAttribute userData ud,Model model) {
////		
////		System.out.println(ud);
////		ud.setPassword(bp.encode(ud.getPassword()));
////		ud.setRole("ROLE_USER");
////		repository.save(ud);
////		model.addAttribute("message", "User Register successfully...");
////		
////		return "redirect:/login";
////	}
//	
//	
//}
