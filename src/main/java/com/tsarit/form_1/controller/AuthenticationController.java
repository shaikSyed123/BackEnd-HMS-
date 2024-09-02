package com.tsarit.form_1.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tsarit.form_1.config.AuthenticationRequest;
import com.tsarit.form_1.config.JwtUtil;
import com.tsarit.form_1.model.userData;
import com.tsarit.form_1.repository.userRepository;
import com.tsarit.form_1.responses.LoginResponse;
import com.tsarit.form_1.service.AppointmentService;
import com.tsarit.form_1.service.OtpService;

import jakarta.servlet.http.HttpServletRequest;

import com.tsarit.form_1.config.coustomUserDetailService;

@Controller
public class AuthenticationController {
	
	@Autowired(required=true)
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtii;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private coustomUserDetailService coustomUserDetailService;
	@Autowired
	private userRepository repository;
	
	@Autowired
	private LoginResponse LoginResponse;
	
	@Autowired
	private BCryptPasswordEncoder bp;
	
	 @Autowired
	    private OtpService otpService;

//	    @PostMapping("/send-otp")
//	    public ResponseEntity<String> sendOtp(
////	    		@RequestParam("emailid") String emailid
//	    		@RequestBody Map<String, String> request
//	    		) {
//	    	 String emailid = request.get("email");
//	        try {
//	            String otp = otpService.sendOtp(emailid);
//	            return ResponseEntity.ok("OTP sent successfully to " + emailid);
//	        } catch (Exception e) {
//	            return ResponseEntity.status(500).body("Failed to send OTP: " + e.getMessage());
//	        }
//	    }
	 
	 @GetMapping(value = {"/", "/{path:[^\\.]*}"})
	    public String index() {
	        // Forward to index.html
	        return "forward:/index.html";
	    }
	 
	 
	  @PostMapping("/resend-otp")
	    public ResponseEntity<String> resendOtp(@RequestBody userData ud) {
		  System.out.println("resended Received data: " + ud);
	        try {
	        	
	     	    String otp = otpService.generateOtp();

	             // Save temporary user data and OTP
	             otpService.saveTemporaryUser(ud, otp);

	             // Send the OTP to the user's email
	             otpService.sendOtp(ud.getEmailid());
	             System.out.println("otp resende :"+otp);
	            return ResponseEntity.ok("OTP has been resent successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to resend OTP.");
	        }
	    }
	    
	    
	    @PostMapping("/verify-otp")
	    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
	    	 System.out.println("Received request: " + request);
	        String emailid = request.get("emailid");
	        String otp = request.get("otp");

	        boolean isOtpValid = otpService.verifyOtp(emailid, otp);

	        if (isOtpValid) {
	            userData ud = otpService.getTemporaryUser(emailid);
	            System.out.println(ud);
	            if (ud != null) {
	                // Save the user data to the database
	                repository.save(ud);
	                otpService.clearTemporaryData(emailid); // Clear temporary data
	                return ResponseEntity.ok("OTP verified successfully. User registered.");
	            } else {
	                return ResponseEntity.status(400).body("No user data found for the provided email.");
	            }
	        } else {
	            return ResponseEntity.status(400).body("Invalid OTP.");
	        }
	    }

	
	
	
	  @PostMapping("/register-react")
	public ResponseEntity<String> register(@RequestBody userData ud) {
	    System.out.println("Received data: " + ud);
	    ud.setPassword(bp.encode(ud.getPassword()));
	    ud.setRole("ROLE_USER");
//	    repository.save(ud);
	    String otp = otpService.generateOtp();

        // Save temporary user data and OTP
        otpService.saveTemporaryUser(ud, otp);

        // Send the OTP to the user's email
        otpService.sendOtp(ud.getEmailid());
        
	    return ResponseEntity.ok("otp sended successfully");
	}
	

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
	    try {
	        if (authenticationRequest.getEmailid() == null || authenticationRequest.getEmailid().isEmpty() ||
	            authenticationRequest.getPassword() == null || authenticationRequest.getPassword().isEmpty()) {
	            throw new IllegalArgumentException("Email ID and Password cannot be null or empty");
	        }

	        final Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailid(), authenticationRequest.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        userData user = (userData) authentication.getPrincipal();
	        String jwtToken = jwtUtii.generateToken(user.getEmailid());
	        
	        // Store the logged-in user's email ID
	        appointmentService.storeLoggedInUserEmail(user.getEmailid());
	        appointmentService.updateLastLoginDate();

	        String tableName = appointmentService.getLoggedInUsername();
	        appointmentService.data();
	        LoginResponse.setToken(tableName);
	      String  token=LoginResponse.getToken();
            System.out.println("token :"+token);	        
 
            
            appointmentService.createUserTable(token);
            
	        return ResponseEntity.ok(LoginResponse);
	    } catch (Exception e) {
	        e.printStackTrace(); // Print stack trace for debugging
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }
	}
	
		@GetMapping("/dashboard")
		public ResponseEntity<?> getDashboard(
				@RequestParam(value = "username", required = false) String username
				) 
		{    
		 
		    Map<String, Object> response = new HashMap<>();
	       String token=LoginResponse.getToken();
	        System.out.println(token);
 
	        System.out.println("username:"+ username);
		    try {
		        // If username is null, handle it appropriately
		        if (username == null || username.isEmpty()) {
//		            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Username is required"));
		        	 username =jwtUtii.getUsernameFromToken(username);
		        }
		    	 // Extract the actual token by removing the "Bearer " prefix

		        // Fetch patient count
		        long TotalpatientCount = appointmentService.TotalPatientCount(token);
		        response.put("TotalpatientCount", TotalpatientCount);
		        System.out.println(TotalpatientCount);
		        
	            long TodaypatientCount=appointmentService.countTodayAppointments(token);
	            response.put("TodaypatientCount", TodaypatientCount);
	            System.out.println(TodaypatientCount);
		        // Fetch user data
	            long TotalAmmount=appointmentService.TotalAmount(token);
	            response.put("totalPayment", TotalAmmount);
	            
	            long TodayTotalAmmount=appointmentService.TodayTotalAmount(token);
	            response.put("todayPayment", TodayTotalAmmount);
	            
	            
	            LocalDate currentDate = LocalDate.now();
	            LocalTime currentTime = LocalTime.now();
	            
	            LocalDateTime lastLoginDate = appointmentService.getLastLoginDate();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	            String formattedDate = lastLoginDate.format(formatter);
		        userData user = appointmentService.data();
		        if (user != null) {
		            response.put("firstname", user.getFirstname());
		            response.put("lastname", user.getLastname());
		            response.put("hospitalname", user.getHospitalname());
		            response.put("currentDate", currentDate);
		            response.put("currentTime", currentTime);
		            response.put("formattedDate", formattedDate);
		        } else {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "User not found"));
		        }
                System.out.println("response :"+response);
		        return ResponseEntity.ok(response);

		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An internal error occurred"));
		    }
		}
	 @PostMapping("/saveAthentication")
	    public ResponseEntity<String> saveUserData(
	    		@RequestBody Map<String, String> userData) {
		 userData.forEach((key, value) -> System.out.println("Param: " + key + " = " + value));
	        try {
	        	appointmentService.saveUserData( userData);
	            return ResponseEntity.ok("User data saved successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error saving user data: " + e.getMessage());
	        }
	    }
	 @PostMapping("/reactlogout")
	    public ResponseEntity<String> logout(HttpServletRequest request) {
	        // Invalidate the current user session
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null) {
	            SecurityContextHolder.getContext().setAuthentication(null);
	            System.out.println("logout successfull");
	        }
	        // Here you could also invalidate the JWT or perform other logout operations if needed

	        return ResponseEntity.ok("Logged out successfully.");
	    }
	 @PostMapping("/savetreatment")
	    public ResponseEntity<String> save(
	    		@RequestBody Map<String, String> userData) {
		 userData.forEach((key, value) -> System.out.println("Param: " + key + " = " + value));
	        try {
	        	appointmentService.savetreatment( userData);
	            return ResponseEntity.ok("treatment data saved successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error saving user data: " + e.getMessage());
	        }
	    }
	 
	 @GetMapping("/api/records")
	    public ResponseEntity<List<Map<String, Object>>> getAllRecords(@RequestParam(value = "username", required = false) String username,@RequestParam(value = "keyword", required = false) String keyword) {
		 username=appointmentService.getLoggedInUsername();
	        try {
	            List<Map<String, Object>> records = appointmentService.fetchData(username);
	            return ResponseEntity.ok(records);
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body(null);
	        }
	 }
	 
	 @GetMapping("/api/record/{patientId}")
	    public ResponseEntity<List<Map<String, Object>>> getRecords(@PathVariable int patientId) {
	        try {
	        	 System.out.println("Received ID: " + patientId);

	             // Convert the ID from String to Integer
	            List<Map<String, Object>> records = appointmentService.fetch(patientId);
	            return ResponseEntity.ok(records);
	        } catch (Exception e) {
	             e.printStackTrace();
	             return null;
	        }
	 }
	 
	 @GetMapping("/get/api/")
	    public ResponseEntity<userData> get() {
	        try {
	          userData records = appointmentService.fetch();
	            return ResponseEntity.ok(records);
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body(null);
	        }
	 }
	    
	    @GetMapping("/api/payments/today")
	    public ResponseEntity<List<Map<String, Object>>> TotalPatient() {
	    	try {
	    		List<Map<String, Object>> records=appointmentService.TotalPatient();
	    		return ResponseEntity.ok(records);
			} catch (Exception e) {
				// TODO: handle exception
				 return ResponseEntity.status(500).body(null);
			}
	    }
	    
	    @GetMapping("/delete/{id}")
	    public ResponseEntity<String> deleteRecord(@PathVariable int id) {
	    	try {
	    		appointmentService.delete(id);
	    		return ResponseEntity.ok("Record deleted successfully");
			} catch (Exception e) {
				// TODO: handle exception
				return ResponseEntity.status(404).body("Record not found");
			}
	    }
	    
	    @GetMapping("/monthly-total")
	    public ResponseEntity<Map<String, Object>> getMonthlyTotalAmount() {
	    	try {
	            Map<String, Object> totals = appointmentService.getMonthlyAndYearlyTotalAmount();
	            return ResponseEntity.ok(totals);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body(Map.of("error", "An error occurred while fetching the data"));
	        }
	    }
	    
	    
	    @PostMapping("/reset-password")
	    public ResponseEntity<String> resetPassword(@RequestBody userData ud)
	    {
	        try {
	        	String email=ud.getEmailid();
	        	String password=bp.encode(ud.getPassword());
	        	String repetepassword=ud.getPassword();
	        	
                 appointmentService.forgotPassword(email,password,repetepassword);	        	
	            return ResponseEntity.ok("Password has been reset successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error: " + e.getMessage());
	        }
	    }
	    @PostMapping("/forgot-otp/{emailid}")
	    public ResponseEntity<String> forgotOtp(@PathVariable String emailid) {
	    	System.out.println(emailid);
	      userData	ud=appointmentService.fetch2(emailid);
		  System.out.println("resended Received data: " + ud);
	        try {
	        
	     	    String otp = otpService.generateOtp();

	             // Save temporary user data and OTP
	             otpService.saveTemporaryUser(ud, otp);

	             // Send the OTP to the user's email
	             otpService.sendOtp(emailid);
	             System.out.println("otp resende :"+otp);
	            return ResponseEntity.ok("OTP has been resent successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to resend OTP.");
	        }
	    }
	    
	    
	    @PostMapping("/update/{id}")
	    public ResponseEntity<String> updateUserData(
	    		@RequestBody Map<String, String> userData,@PathVariable("id") int id) {
		 userData.forEach((key, value) -> System.out.println("Param: " + key + " = " + value));
	        try {
	        	appointmentService.updateAppointment(userData,id);
	            return ResponseEntity.ok("User data saved successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error saving user data: " + e.getMessage());
	        }
	    }
	    

}

